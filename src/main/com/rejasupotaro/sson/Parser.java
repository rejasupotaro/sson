package main.com.rejasupotaro.sson;

import java.io.File;
import java.io.IOException;



public class Parser {

    Scanner scanner;
    final static int INLIST = 1;
    final static int NOTINLIST = 2;
    final static int TOKENAFTERDOT = 3;
    final static int DOTFINISHED = 4;
    Sexpr NIL = new Sexpr("NIL");

    public Parser(String label, String value) {
        this("(" + label + " " + value + ")");
    }

    public Parser(String textValue) {
        this.scanner = new Scanner(textValue);
    }

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    public Sexpr buildTree() throws IOException {
        return buildTree(null);
    }

    public Sexpr buildTree(Token lastToken) throws IOException {
        Token token = new Token();
        Sexpr sexpr = new Sexpr("NULL");
        Sexpr head = sexpr;
        int state = NOTINLIST;
        int isNilList = 1;
        token = lastToken;

        if (lastToken == null) {
            token = this.scanner.getNextToken();
            if (token.type == TokenType.FILEEOF) {
                return null;
            }
        }

        do{
            switch(token.type)
            {
            case TokenType.LPAREN:
            {
                switch(state)
                {
                case INLIST:
                {
                    sexpr.car = buildTree(token);
                    Sexpr sexprNew = new Sexpr("NIL"); 
                    sexpr.cdr = sexprNew;
                    sexpr.isAtom = false;//Make the cell non-atom?
                    sexpr.isNIL = false;//...
                    sexpr.isNumber = false;//...
                    sexpr.isID =false;
                    sexpr = sexprNew;
                    sexpr.car = null;
                    sexpr.cdr = null;
                    sexpr.isAtom = true;
                    sexpr.isNIL = true;
                    sexpr.isNumber = false;
                    sexpr.isID = false;
                    isNilList = 0;
                    continue;
                }
                case NOTINLIST:
                {
                    state = INLIST;
                    isNilList = 1;
                    continue;                               
                }
                case TOKENAFTERDOT:
                {
                    Sexpr tmp = new Sexpr("NIL");
                    tmp = buildTree(token);
                    sexpr.car = tmp.car;
                    sexpr.cdr = tmp.cdr;
                    sexpr.isAtom = false;
                    sexpr.isNIL = false;
                    sexpr.isNumber = false;
                    sexpr.isID = false;
                    //sexpr.cdr = buildTree(token);
                    state = DOTFINISHED;
                    continue;
                }
                case DOTFINISHED:
                {
                    System.err.println("Syntax error: more than one object follows dot in list");
                    return head;                                
                }
                }
                break;
            }
            case TokenType.RPAREN:
            {
                switch(state)
                {
                case NOTINLIST:
                {
                    System.err.println("Unmatched Right Parenthisis.");
                    break;
                }
                case INLIST:
                {
                    return head;
                }
                }
                return head;
            }
            case TokenType.DOT:
            {
                switch(state)
                {
                case INLIST:
                {
                    if(isNilList == 1)
                    {
                        System.err.println("Syntax error: the list is dot start");
                        return head;                                    
                    }
                    state = TOKENAFTERDOT;
                    break;
                }
                case NOTINLIST:
                {
                    System.err.println("Syntax error: a dot outside the parenthesis.");
                    return head;                                
                }
                case TOKENAFTERDOT:
                {
                    System.err.println("Syntax error: two consecutive dots.");
                    return head;
                }
                case DOTFINISHED:
                {
                    System.err.println("Syntax error: More than one object follows dot in list");
                    return head;
                }                               
                }
                break;
            }
            case TokenType.INT:
            {
                switch(state)
                {
                case INLIST:
                {

                    //isNilList = 0;
                    Sexpr sexprNew =new Sexpr(token.value);
                    sexprNew.isNumber = true;
                    sexpr.car = sexprNew;
                    sexpr.car.isNumber = true;
                    //System.out.println("The type of this sexpr is " + sexpr.car.isNumber);
                    sexpr.isAtom = false;//Make the cell become nonatom?
                    sexpr.isNIL = false;//...

                    sexpr.isNumber = false;//...
                    sexpr.isID = false;

                    //sexprNew.isAtom = false;
                    sexprNew.isAtom = true;
                    sexprNew.isNIL = false;
                    if(sexprNew.value.equals("NIL"))
                        sexprNew.isNIL = true;
                    //sexprNew.isNumber = false;
                    sexprNew.isNumber = true;
                    sexprNew.isID = false;

                    sexprNew = new Sexpr("NIL");
                    sexpr.cdr = sexprNew;
                    sexpr = sexprNew;
                    sexpr.car = null;
                    sexpr.cdr = null;
                    sexpr.isAtom = true;
                    sexpr.isNIL = true;
                    sexpr.isNumber = false;
                    isNilList = 0;                                                              
                    continue;
                }
                case NOTINLIST:
                {
                    sexpr.isAtom = true;
                    sexpr.isNumber = true;
                    sexpr.isNIL = false;
                    sexpr.value = token.value;
                    return sexpr;
                }
                case TOKENAFTERDOT:
                {                               
                    sexpr.isAtom = true;
                    if(token.type == TokenType.INT)
                    {
                        Sexpr sexprNew =new Sexpr(token.value);
                        sexpr.value = sexprNew.value;
                        sexpr = sexprNew;
                        sexpr.isAtom = true;
                        sexpr.isNumber = true;
                        sexpr.isID = false;
                    }
                    else if(token.type == TokenType.ID)
                    {
                        Sexpr sexprNew =new Sexpr(token.value);
                        sexpr.value = sexprNew.value;
                        sexpr = sexprNew;
                        sexpr.isAtom = true;
                        sexpr.isNumber = false;
                        sexpr.isID = true;
                        if(sexprNew.value.equals("NIL"))
                            sexprNew.isNIL = true;
                    }
                    state = DOTFINISHED;
                    break;
                }
                case DOTFINISHED:
                {
                    System.err.println("Syntax error: More than one object follows dot in list");
                    return head;                                
                }
                }
                break;
            }
            case TokenType.ID:
            {
                switch(state)
                {
                case INLIST:
                {                                   

                    //isNilList = 0;
                    Sexpr sexprNew =new Sexpr(token.value);
                    sexpr.car = sexprNew;

                    sexpr.isAtom = false;//Make the cell become nonatom?
                    sexpr.isNIL = false;//...

                    sexpr.isNumber = false;//...
                    sexpr.isID = false;


                    //sexprNew.isAtom = false;
                    sexprNew.isAtom = true;
                    sexprNew.isNIL = false;
                    if(sexprNew.value.equals("NIL"))
                        sexprNew.isNIL = true;
                    sexprNew.isNumber = false;
                    sexprNew.isID = true;
                    sexprNew = new Sexpr("NIL");
                    sexpr.cdr = sexprNew;
                    sexpr = sexprNew;
                    sexpr.car = null;
                    sexpr.cdr = null;
                    sexpr.isAtom = true;
                    sexpr.isNIL = true;
                    sexpr.isNumber = false;
                    sexpr.isID = false;
                    isNilList = 0;

                    continue;
                }
                case NOTINLIST:
                {
                    //sexpr.isAtom = true;
                    sexpr.isAtom = true;
                    sexpr.isNumber = true;
                    sexpr.isNIL = false;
                    sexpr.value = token.value;
                    return sexpr;           
                }
                case TOKENAFTERDOT:
                {                               
                    /*                              sexpr.Value = token.Value;
                                sexpr.isID = true;
                                sexpr.isAtom = true;
                                sexpr.isNIL = false;
                                if(sexpr.Value.equals(sexpr));
                                    sexpr.isNIL = true;
                                sexpr.isNumber = false;
                                state = DOTFINISHED;*/
                    //return head;
                    sexpr.isAtom = true;
                    if(token.type == TokenType.INT)
                    {
                        Sexpr sexprNew =new Sexpr(token.value);
                        sexpr.value = sexprNew.value;
                        sexpr = sexprNew;
                        sexpr.isAtom = true;
                        sexpr.isNumber = true;
                        sexpr.isID = false;
                    }
                    else if(token.type == TokenType.ID)
                    {
                        Sexpr sexprNew =new Sexpr(token.value);
                        sexpr.value = sexprNew.value;
                        sexpr = sexprNew;
                        sexpr.isAtom = true;
                        sexpr.isNumber = false;
                        sexpr.isID = true;
                        if(sexprNew.value.equals("NIL"))
                            sexprNew.isNIL = true;
                    }
                    state = DOTFINISHED;                                
                    break;
                }
                case DOTFINISHED:
                {
                    System.err.println("Syntax error: More than one object follows dot in list");
                    return head;
                }                            
                }

            }
            break;                      
            case TokenType.FILEEOF:
                return head;                                                        
            }                                       
            /*catch(Exception e)
            {
                token = null;
                break;
            }   */
            /*try {
                token = this.scanner.getNextToken();
            } catch(Exception e)
            {
                token = null;
                break;//TODO
            }*/
        }while((token = this.scanner.getNextToken()) != null );//TODO
        return head;
    }
}
