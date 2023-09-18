public class Test{
    public static void main(String[] args) throws Exception{
        if(args.length != 2){
            System.err.println("Usage: java Test <InputText> <CipherRounds>");
        }
        int cipherRounds = Integer.parseInt(args[1]);
        NSub ns = new NSub(cipherRounds);
        System.out.println(ns);
        String cipherText = ns.encryption(args[0]);
        System.out.println(cipherText);
        String plainText = ns.decryption(cipherText);
        System.out.println(plainText);
    }
}

