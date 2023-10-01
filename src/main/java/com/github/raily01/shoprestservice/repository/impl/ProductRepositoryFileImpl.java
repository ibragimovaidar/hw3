package com.github.raily01.shoprestservice.repository.impl;

import com.github.raily01.shoprestservice.config.ApplicationProperties;
import com.github.raily01.shoprestservice.exception.ProductAlreadyExistsException;
import com.github.raily01.shoprestservice.exception.ProductNotFoundException;
import com.github.raily01.shoprestservice.model.domain.Product;
import com.github.raily01.shoprestservice.repository.ProductRepository;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;
import java.io.FilePermission;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

@ConditionalOnProperty(prefix = "app", value = "repository", havingValue = "file")
@Repository
public class ProductRepositoryFileImpl implements ProductRepository {

    private final Path storagePath;
    private final ReentrantLock storageLock = new ReentrantLock();
    private final Set<String> keyCache = new HashSet<>();

    @SneakyThrows
    public ProductRepositoryFileImpl(ApplicationProperties applicationProperties) {
        this.storagePath = Path.of(applicationProperties.getRepositoryFilePath());
        if (!Files.exists(storagePath)) {
            Files.createFile(storagePath);
        }

        try (var inputStream = Files.newInputStream(
                storagePath,
                StandardOpenOption.CREATE,
                StandardOpenOption.READ)) {
        } catch (IOException e) {
            throw new RuntimeException("Не удалось открыть файл для чтения и записи");
        }
    }

    @Override
    public Optional<Product> getById(String vendorCode) {
        try {
            storageLock.lock();
            if (!keyCache.contains(vendorCode)) {
                Optional.empty();
            }
            var productLine = getLines()
                    .map(line -> line.split("#"))
                    .filter(line -> vendorCode.equals(line[0]))
                    .findFirst()
                    .orElseThrow(() -> new ProductNotFoundException(vendorCode));
            return Optional.of(new Product(productLine[0], productLine[1], Long.valueOf(productLine[2]), Long.valueOf(productLine[3])));
        } finally {
            storageLock.unlock();
        }
    }

    @Override
    public Product save(Product product) {
        try {
            storageLock.lock();
            var key = product.getVendorCode();
            if (keyCache.contains(key)) {
                throw new ProductAlreadyExistsException(key);
            }
            var productLine = String.format("%s,%s,%d,%d",
                    product.getVendorCode(),
                    product.getName(),
                    product.getPrice(),
                    product.getQuantity());
            writeLine(productLine);
            keyCache.add(key);
            return product;
        } finally {
            storageLock.unlock();
        }
    }

    @Override
    public Product update(Product product) {
        throw new RuntimeException();
        // TODO: реализовать
        /*try (var file = new RandomAccessFile(storagePath.toFile(), "rwd")) {
            storageLock.lock();
            var line = file.readLine();
            int offset = 0;
            while (line != null) {
                var lineContent = line.split(",");
                if (product.getVendorCode().equals(lineContent[0])) {
                    file.seek(offset);
                    byte[] buffer = new byte[4096];
                    int read = -1;
                    while ( ((read = file.read(buffer)) > -1)) {
                        file.seek(file.getFilePointer() - read);
                        file.write(buffer, 0, read);
                    }
                    file.setLength(file.length() - line.length());
                    file.seek(file.length());
                    var newLineBytes = productToLine(product).getBytes(StandardCharsets.UTF_8);
                    file.write(newLineBytes, 0, newLineBytes.length);
                    return product;
                }
                offset += line.getBytes(StandardCharsets.UTF_8).length;
                line = file.readLine();
            }
            throw new ProductNotFoundException(product.getVendorCode());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            storageLock.unlock();
        }*/
    }

    @Override
    public void delete(String vendorCode) {
        // TODO: реализовать
        throw new RuntimeException();
    }

    private Stream<String> getLines() {
        try (var reader = Files.newBufferedReader(storagePath)) {
            return reader.lines();
        } catch (IOException e) {
            throw new RuntimeException("Не удалось открыть файл для чтения", e);
        }
    }

    private void writeLine(String line) {
        try (var writer = Files.newBufferedWriter(storagePath, StandardOpenOption.APPEND)) {
            writer.write(line + "\n");
        } catch (IOException e) {
            throw new RuntimeException("Не удалось открыть файл для записи", e);
        }
    }

    private String productToLine(Product product) {
        return String.format("%s,%s,%d,%d\n",
                product.getVendorCode(),
                product.getName(),
                product.getPrice(),
                product.getQuantity());
    }
}
