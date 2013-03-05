package com.rejasupotaro.sson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
public class Scanner {

    public String textString;

    public Scanner(String textString) {
        this.textString = textString.toUpperCase();;
    }

    public Scanner(File file) {
        try {
            this.textString = readFileToString(file).toUpperCase();;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public String readFileToString(File file) throws IOException {
        FileInputStream  inputStream = new FileInputStream(file);
        StringBuffer buffer = new StringBuffer();
        String line;
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream));
        line = bufferReader.readLine();
        while (line != null) {
            buffer.append(line);
            line = bufferReader.readLine();
        }
        inputStream.close();
        this.textString = buffer.toString();
        return this.textString;
    }

    public Token getNextToken() {
        Token nextToken = new Token();
        if (this.textString == null) {
            nextToken.type = TokenType.FILEEOF;
            return nextToken;
        }

        StringTokenizer stringtokenizer = new StringTokenizer(this.textString); 
        if (stringtokenizer.hasMoreElements() == false) {
            nextToken.type = TokenType.FILEEOF;
            return nextToken;
        }

        String token= stringtokenizer.nextToken();  

        if ((token.charAt(0)) == '(') {
            nextToken.type = TokenType.LPAREN;
            int i = 0;
            while (true) {
                if(this.textString.charAt(i) != '(') {
                    i++;
                } else {
                    break;
                }
            } if (i != 0) {
                token = this.textString.substring(0,i);
                this.textString = this.textString.substring(i);
            } else {
                token = Character.toString(this.textString.charAt(0));
                this.textString = this.textString.substring(1);
            }   
            this.textString = this.textString.substring(i);
            return nextToken;           
        } else if ((token.charAt(0)) == ')') {
            nextToken.type = TokenType.RPAREN;
            this.textString = this.textString.substring(1); 
            return nextToken;           
        } else if ((token.charAt(0)) == '.') {
            nextToken.type = TokenType.DOT;
            this.textString = this.textString.substring(this.textString.indexOf(token.charAt(0))+1);    
            return nextToken;   
        }

        if (this.judgeIDorInteger(token) == 1) {
            nextToken.type = TokenType.INT;
            nextToken.value = token;
            if (this.textString.length() > token.length()) {
                this.textString = this.textString.substring(token.length()+1);
            } else {
                this.textString = null;
            }

            return nextToken;
        } else if (this.judgeIDorInteger(token) == 2) {
            nextToken.type = TokenType.ID;
            nextToken.value = token;
            if (this.textString.length() > token.length()) {
                this.textString = this.textString.substring(token.length()+1);
            } else {
                this.textString = null;
            }
            return nextToken;
        } else if (this.judgeIDorInteger(token) == 3) {
            int i = 0;
            while(true) {
                if (this.textString.charAt(i) != '(') {
                    i++;
                } else {
                    break;
                }
            }
            if (i != 0) {
                token = this.textString.substring(this.textString.indexOf(token.charAt(0)),i);
                this.textString = this.textString.substring(i);
            } else {
                token = Character.toString(this.textString.charAt(0));
                this.textString = this.textString.substring(1);
            }
            nextToken.value = token;
            this.textString = this.textString.substring(i);
            return nextToken;
        } else if (this.judgeIDorInteger(token) == 4) {
            int i = 0;
            while (true) {
                if (this.textString.charAt(i) != ')') {
                    i++;
                } else {
                    break;
                }
            }
            if (i != 0) {
                token = this.textString.substring(this.textString.indexOf(token.charAt(0)),i);
                this.textString = this.textString.substring(i);
            } else {
                token = Character.toString(this.textString.charAt(0));
                this.textString = this.textString.substring(1);
            }
            nextToken.value = token;
            if (this.judgeIDorInteger(token) == 1) {
                nextToken.type = TokenType.INT;
            } else if(this.judgeIDorInteger(token) == 2) {
                nextToken.type = TokenType.ID;
            } else {
                return null;
            }

            return nextToken;
        } else if (this.judgeIDorInteger(token) == 5) {
            int i = 0;
            while (true) {
                if (this.textString.charAt(i) != '.') {
                    i++;
                } else {
                    break;
                }
            }
            if (i != 0) {
                token = this.textString.substring(0,i);
                this.textString = this.textString.substring(i);
            } else {
                token = Character.toString(this.textString.charAt(0));
                this.textString = this.textString.substring(1);
            }
            nextToken.value = token;
            if (this.judgeIDorInteger(token) == 1) {
                nextToken.type = TokenType.INT;
            } else if(this.judgeIDorInteger(token) == 2) {
                nextToken.type = TokenType.ID;
            } else {
                return null;
            }
            return nextToken;
        } else if((this.judgeIDorInteger(token) < 0)) {
            return null;
        }
        return null;
    }

    public int judgeIDorInteger(String s) {
        int i = 0;
        int count = 0;
        if ((s.charAt(0) >= '0') && (s.charAt(0) <= '9')) {
            count = 1;          
            for (i = 1; i < s.length(); i++) {
                if ((s.charAt(i) >= '0') && (s.charAt(i) <= '9')) {
                    count ++;
                } else if(s.charAt(i) == '(') {
                    return 3;
                } else if(s.charAt(i) == ')') {
                    return 4;
                } else if(s.charAt(i) == '.') {
                    return 5;
                }
            }

            if (count == s.length()) {
                return 1;
            } else {
                return -1;
            }
        }
        if ((s.charAt(0) >= 'A') && (s.charAt(0) <= 'Z')) {
            count  = 1;
            for(i = 1; i < s.length(); i++) {
                if (((s.charAt(i) >= 'A') && (s.charAt(i) <= 'Z'))) {
                    count++;
                } else if((s.charAt(i) >= '0') && (s.charAt(i)) <= '9') {
                    count++;
                } else if(s.charAt(i) == '(') {
                    return 3;
                } else if(s.charAt(i) == ')') {
                    return 4;
                } else if(s.charAt(i) == '.') {
                    return 5;
                }
            }

            if (count == s.length()) {
                return 2;
            } else {
                return -2;
            }
        } else {
            return -3;
        }
    }
    public static void main(String[] args) throws Exception {
        Scanner scanner =new Scanner(new File("/home/lixinyu/workspace/Lisp/src/test.txt"));
//        scanner.readFileToString(filename);
        Token token;
//        scanner.readFileToString(filename);
        while(true)
        {
            try
            {
                token = scanner.getNextToken();

            }catch(Exception e)
            {
                token = null;
            }
            if(token.type != TokenType.FILEEOF)
            {
                switch(token.type)
                {
                case TokenType.DOT:
                {
                    //System.out.println("Dot");
                    System.out.print(". ");
                    break;
                }
                case TokenType.INT:
                {
                    //System.out.println("Integer " + token.Value);
                    System.out.print(token.value);
                    break;
                }
                case TokenType.ID:
                {
                    //System.out.println("ID " + token.Value);
                    System.out.print(token.value);
                    break;
                }
                case TokenType.LPAREN:
                {
                    //System.out.println("LPAREN");
                    System.out.print("(");
                    break;
                }
                case TokenType.RPAREN:
                {
                    //System.out.println("RPAREN");
                    System.out.print(")");
                    break;
                }               
                }
            }
            else
                break;
        }
    }
}