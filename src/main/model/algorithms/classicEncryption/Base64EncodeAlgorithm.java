package main.model.algorithms.classicEncryption;

import main.model.algorithms.AAlgorithm;
import main.model.algorithms.IAlgorithms;
import main.model.key.IKey;

import java.io.*;
import java.util.Base64;

public class Base64EncodeAlgorithm extends AAlgorithm {


    @Override
    public void genKey() {

    }


    @Override
    public String encrypt(String input) {
        byte[] data = input.getBytes();
        StringBuilder sb = new StringBuilder();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        byte[] buffer = new byte[102300];
        try {
            int read = 0;
            while ((read = bis.read(buffer)) != -1) {
                if (read < buffer.length) {
                    byte[] temp = new byte[read];
                    System.arraycopy(buffer, 0, temp, 0, read);
                    buffer = temp;
                }
                sb.append(Base64.getEncoder().encodeToString(buffer));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
    public String decrypt(String encryptInput) {
        byte[] data = encryptInput.getBytes();
        StringBuilder sb = new StringBuilder();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        byte[] buffer = new byte[102300];
        try {
            int read = 0;
            while ((read = bis.read(buffer)) != -1) {
                if (read < buffer.length) {
                    byte[] temp = new byte[read];
                    System.arraycopy(buffer, 0, temp, 0, read);
                    buffer = temp;
                }
                sb.append(new String(Base64.getDecoder().decode(buffer)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    @Override
    public boolean encryptFile(String input, String output) {
        File file = new File(input);
        StringBuilder sb = new StringBuilder();
        BufferedInputStream bis = null;
        ByteArrayInputStream bais = null;
        PrintWriter pw = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            byte[] bytes = new byte[102300];
            int read = 0;
            while ((read = bis.read(bytes)) != -1) {
                if (read < bytes.length) {
                    byte[] temp = new byte[read];
                    System.arraycopy(bytes, 0, temp, 0, read);
                    bytes = temp;
                }
                sb.append(Base64.getEncoder().encodeToString(bytes));
            }
            bais = new ByteArrayInputStream(sb.toString().getBytes());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(output));
            byte[] buffer = new byte[102300];
            while ((read = bais.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
            bos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean decryptFile(String input, String output) {
        File file = new File(input);
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        ByteArrayInputStream bais = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            char[] chars = new char[102300];
            int read = 0;
            while ((read = br.read(chars)) != -1) {
                sb.append(chars, 0, read);
            }
            byte[] bytes = Base64.getDecoder().decode(sb.toString());
            bais = new ByteArrayInputStream(bytes);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(output));
            byte[] buffer = new byte[102300];
            while ((read = bais.read(buffer)) != -1) {
                bos.write(buffer, 0, read);
            }
            bos.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Base64EncodeAlgorithm base64EncodeAlgorithm = new Base64EncodeAlgorithm();
        String input = "Hello World";
        String encrypt = base64EncodeAlgorithm.encrypt(input);
        base64EncodeAlgorithm.encryptFile("src/testData/21130171.jpg", "src/testData/encrypt.txt");
        base64EncodeAlgorithm.decryptFile("src/testData/encrypt.txt", "src/testData/decrypt.jpg");

    }
}
