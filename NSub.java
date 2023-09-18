import java.util.Random;
/**
 * This class is used to construct a N-subsitution cipher.
 * N character shift frame are created and used in loop to
 * encrypt character in each string. This class only work for
 * purely alphabetical english words.
 * @author Aemon Zhou
 * @version 0.0.1
 */
public class NSub{
    //Least 1 cipher set is required for encryption & decryption.
    private int N = 1;
    //The number of alphabet in a language (26 in English).
    private int alphabetCount = 26;
    //The array that store the cipher sets that will be used in encryption & decryption.
    private char[][] Pattern;
    //The array that contain the loop switch between cipher sets.
    private int[] indexPattern;
    //Randomiser 1 used in generate cipher sets & indexpattern.
    private Random r1;
    //Randomiser 2 used in generate cipher sets.
    private Random r2;
    //Number of rounds of randomisation.
    private int randomTimes = 100;
    
    /**
     * Default and the only constructor available, takeing an integer as input
     * to dicate the number of set of cipher used. The higher the number of sets,
     * the harder the cipher is to be breaked.
     * @param N the number of cipher sets will be generated, for which N > 1,
     */
    public NSub(int N){
        if(N > 1) this.N = N;
        generatePattern();
        generateIndexPattern();
    }
    
    /**
     * Complete a encryption on a plain english text (without numeric value).
     * When text length is longer than the number of cipher sets available,
     * the cipher sets will be used in loop.
     * @return A String object contain the encrypted text.
     */
    public String encryption(String plainText){
        if(plainText == null) throw new NullPointerException("Please ensure text used for encrption is not null");
        if(plainText.equals("") || plainText.length() == 0) throw new IllegalArgumentException("Please ensure text used for encryption is not empty");
        int shifts = 0;
        boolean capital;
        char current;
        int index;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < plainText.length(); i++){
            if(shifts == indexPattern.length) shifts = 0;
            capital = (Character.isLowerCase(plainText.charAt(i))) ? false: true;//If lower case is true, then capital would be false.
            current = Character.toLowerCase(plainText.charAt(i));//Default to only deal with lower case to simplify complexity.
            index = (int)current - 97;//e.g. index for a will be 0.
            current = Pattern[indexPattern[shifts]][index];
            sb.append((capital==true) ? Character.toUpperCase(current): current);
            shifts++;
        }
        return sb.toString();
    }
    
    /**
     * Complete a decryption on a cipher english text (without numeric value).
     * When text length is longer than the number of cipher sets available,
     * the cipher sets will be used in loop.
     * @return A Sttring object contain the plaintext/decrypted text.
     */
    public String decryption(String cipherText) throws Exception{
        if(cipherText == null) throw new NullPointerException("Please ensure text used for decryption is not null");
        if(cipherText.equals("") || cipherText.length() == 0) throw new IllegalArgumentException("Please ensure text used for decryption is not empty");
        int shifts = 0;
        boolean capital;
        char current;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < cipherText.length(); i++){
            if(shifts == indexPattern.length) shifts = 0;
            capital = (Character.isLowerCase(cipherText.charAt(i))) ? false: true;//If lower case is true, then capital would be false.
            current = Character.toLowerCase(cipherText.charAt(i));
            current = (char)(locateIndex(indexPattern[shifts], current) + 97);
            sb.append((capital==true) ? Character.toUpperCase(current): current);
            shifts++;
        }
        return sb.toString();
    }
    
    private int locateIndex(int row, char target) throws Exception{
        for(int i = 0; i < alphabetCount; i++){
            if(Pattern[row][i]==target) return i;
        }
        throw new Exception("Cannot find the particular character for decryption");
    }
    
    private void generatePattern(){
        Pattern = new char[N][alphabetCount];
        for(int j = 0; j < N; j++){
            char start = 'a';
            for(int i = 0; i < alphabetCount; i++){
                Pattern[j][i] = start;
                start = (char)(start + 1);
            }
        }
        //Now we have random copy and randomise each row.
        for(int j = 0; j < N; j++){
            r1 = new Random();
            r2 = new Random();
            for(int i = 0; i < randomTimes; i++){
                randomiser(j, r1, r2);
            }
        }
    }
    
    private void generateIndexPattern(){
        indexPattern = new int[N];
        for(int i = 0; i < N; i++){
            indexPattern[i] = i;
        }
        //Generate a random pattern;
        for(int i = 0; i < randomTimes; i++){
            indexRandomiser();
        }
    }
    
    private void randomiser(int row, Random random1, Random random2){
        int index1 = random1.nextInt(alphabetCount);
        int index2 = random2.nextInt(alphabetCount);
        char temp = Pattern[row][index1];
        Pattern[row][index1] = Pattern[row][index2];
        Pattern[row][index2] = temp;
    }
    
    private void indexRandomiser(){
        r1 = new Random();
        int index1 = r1.nextInt(N);
        int index2 = r1.nextInt(N);
        int temp = indexPattern[index1];
        indexPattern[index1] = indexPattern[index2];
        indexPattern[index2] = temp;
    }
    
    @Override
    public String toString(){
        String accumulator = "";
        for(int i = 0; i < N; i++){
            accumulator += "[";
            for(int j = 0; j < alphabetCount; j++){
                accumulator += Pattern[i][j] + " ";
            }
            accumulator += "]";
            accumulator += "\r\n";
        }
        accumulator += "[";
        for(int i = 0; i < N; i++){
            accumulator += indexPattern[i] + " ";
        }
        accumulator += "]";
        return accumulator;
    }
}

