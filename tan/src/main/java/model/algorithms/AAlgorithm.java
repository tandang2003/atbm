package model.algorithms;


import model.common.ICipherEnum;
import model.common.KeyPairAlgorithm;
import model.key.IKey;

import java.io.*;
import java.util.Arrays;
import java.util.List;
/**
 * Abstract base class for encryption and decryption algorithms.
 * Cung cấp các chức năng chung để xử lý khóa mật mã và bộ ký tự.
 */
public abstract class AAlgorithm implements IAlgorithms {
    /**
     * Khóa mật mã được sử dụng bởi thuật toán.
     */
    protected IKey key;

    /**
     * Danh sách các ký tự được sử dụng bởi thuật toán.
     */
    protected List<String> arrChar;

    /**
     * Constructor mặc định cho thuật toán.
     */
    protected AAlgorithm() {
    }

    /**
     * Tải khóa mật mã từ tệp được chỉ định.
     *
     * @param selectedFile tệp chứa khóa.
     * @throws IOException nếu tệp không tồn tại hoặc xảy ra lỗi khi đọc.
     */
    @Override
    public void loadKey(File selectedFile) throws IOException {
        if (!selectedFile.exists()) {
            throw new IOException("File not found");
        }
        DataInputStream inputStream = new DataInputStream(new FileInputStream(selectedFile));
        key.loadFromFile(inputStream);
        inputStream.close();
    }

    /**
     * Thiết lập bộ ký tự cho thuật toán.
     *
     * @param chars danh sách các ký tự.
     */
    public void setArrChar(List<String> chars) {
        this.arrChar = chars;
    }

    /**
     * Lấy khóa mật mã được sử dụng bởi thuật toán.
     *
     * @return khóa mật mã.
     */
    @Override
    public IKey getKey() {
        return key;
    }

    /**
     * Lưu khóa mật mã vào tệp được chỉ định.
     * Nếu tệp không tồn tại, nó sẽ được tạo mới.
     *
     * @param selectedFile tệp để lưu khóa.
     * @throws IOException nếu khóa chưa được tạo hoặc xảy ra lỗi khi ghi.
     */
    @Override
    public void saveKey(File selectedFile) throws IOException {
        boolean isFile = selectedFile.getAbsolutePath().contains(".");
        String path = selectedFile.getAbsolutePath();
        if (isFile) {
            File parent = selectedFile.getParentFile();
            path = parent.getAbsolutePath();
            if (!parent.exists()) {
                parent.mkdirs();
            }
        } else {
            if (!selectedFile.exists())
                selectedFile.mkdirs();
        }
        if (key == null) {
            throw new IOException("Key is not created");
        }
        ICipherEnum cipher = getCipher();
        String nameFile = getCipher().getName() + ".tan.key";
        if (cipher instanceof KeyPairAlgorithm) {
            nameFile = "Sign_" + getCipher().getName() + ".tan.key";
        }
        nameFile = Arrays.stream(nameFile.split(""))
                .map(ch -> ch.matches("[a-zA-Z0-9._-]") ? ch : "_")
                .reduce("", String::concat);
        try {
            File destination = new File(path + File.separator + nameFile);
            if (!destination.exists())
                destination.createNewFile();
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(destination));
            key.saveToFile(outputStream);
            outputStream.close();
        } catch (IOException e) {
            throw new IOException("Error while saving key. Please try again");
        }
    }
}



