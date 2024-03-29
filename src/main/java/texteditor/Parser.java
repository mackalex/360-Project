package main.java.texteditor;

import java.util.ArrayList;

public class Parser {

    //defaults
    private static int justified = 0;//0=left, 1 = center, 2 = right, 3 = title center
    private static int space = 1;//change to bool?, 0=double, 1 =single
    private static int indent = 0;// 0 = none, 1 =1st line, 2 =multiple lines
    private static int columns = 0; // 0 = single, 1 = two
    private static int n = 80;
    private static ArrayList<String> output = new ArrayList<String>();

    public static ArrayList<String> output() {
        return output;
    }

    public static void run(ArrayList<String> inputlist) {
        justified = 0;
        space = 1;
        indent = 0;
        columns = 0;
        n = 80;
        output = new ArrayList<String>();

        String input = "";
        int command = 0;

        for (int i = 0; i < inputlist.size(); i++) {
            if (inputlist.get(i).length() > 1) {
                command = isCommand(inputlist.get(i).substring(0, 2));
            }

            if (command != 0) {
                if (input != "") {
                    input = input.trim();
                    input = input.replaceAll("  ", " ");
                    if (columns == 0)
                        oneColumn(input);
                    else
                        twoColumn(input);
                    input = "";
                }
            }

            switch (command) {
                //if not a command
                case 0:
                    input = input + inputlist.get(i) + " ";
                    break;
                //justified
                case 1: //left
                    justified = 0;
                    break;
                case 2: //center
                    justified = 1;
                    break;
                case 3: //right
                    justified = 2;
                    break;
                case 4: //title

                    int holder = justified;
                    justified = 1;
                    if (inputlist.size() > i) {
                        input = inputlist.get(i + 1);
                        if (columns == 0)
                            oneColumn(input);
                        else
                            twoColumn(input);
                    }
                    justified = holder;
                    //update the counter
                    i++;
                    input = "";
                    break;
                //space
                case 5: //double
                    space = 0;
                    break;
                case 6: //single
                    space = 1;
                    break;
                //indent
                case 7: //remove indent
                    indent = 0;
                    break;
                case 8: //1st line
                    indent = 1;

                    break;
                case 9: //multiple lines
                    indent = 2;
                    break;
                //columns
                case 10: //1 column
                    columns = 0;
                    n = 80;
                    break;
                case 11: //two column
                    n = 35;
                    columns = 1;
                    break;
                //e
                case 12: //newline
                    output.add("");
                    break;


            }

        }

        if (indent == 1)
            input = "     " + input;

        if (input != "") {
            if (columns == 0)
                oneColumn(input);
            else
                twoColumn(input);
        }
    }
	/*
	static int justified =0;//0=left, 1 = center, 2 = right, 3 = title center
	static int space = 0;//change to bool?, 0=double, 1 =single
	static int indent = 0;// 0 = none, 1 =1st line, 2 =multiple lines
	static int columns = 0; // 0 = single, 1 = two
	 * */

    //print normally 0...(n-1)+spaces
    public static ArrayList<String> leftJustified(String placeholder) {
        ArrayList<String> inputlist = new ArrayList<String>();
        String secondHalf = "";

        if (indent == 2)
            placeholder = "          " + placeholder;

        if (placeholder.length() < (n)) {
            inputlist.add(placeholder);
            return inputlist;
        } else {
            String extrascpaces = "";
            String firsthalf = "";
            for (int i = (n - 1); i > 0; i--) {
                extrascpaces = extrascpaces + " ";
                if (Character.isWhitespace(placeholder.charAt(i))) {
                    secondHalf = placeholder.substring((i + 1), (placeholder.length()));
                    inputlist = (leftJustified(secondHalf));
                    firsthalf = placeholder.substring(0, (i)) + extrascpaces;
                    inputlist.add(0, firsthalf);
                    break;
                }
            }
        }

        if (secondHalf == "")//fix this to break >n char lines into char lines
            inputlist.add(placeholder);

        return inputlist;
    }

    //
    public static ArrayList<String> center(String placeholder) {//reach both ends
        ArrayList<String> inputlist = new ArrayList<String>();
        String secondHalf = "";
        String extrascpaces = "";

        if (placeholder.length() < (n)) {
            int w = n - placeholder.length();

            if (w % 2 == 0) {
                for (int a = 0; a < w / 2; a++)
                    extrascpaces = extrascpaces + " ";
                placeholder = ((extrascpaces + placeholder) + extrascpaces);
            } else {
                for (int a = 0; a < (w - 1) / 2; a++)
                    extrascpaces = extrascpaces + " ";
                placeholder = ((extrascpaces + placeholder) + extrascpaces) + " ";
            }
            inputlist.add(placeholder);
            return inputlist;
        } else {


            for (int i = (n - 1); i > 0; i--) {

                if (Character.isWhitespace(placeholder.charAt(i))) {
                    secondHalf = placeholder.substring((i + 1), (placeholder.length()));
                    inputlist = (center(secondHalf));

                    String firsthalf = placeholder.substring(0, (i));

                    int w = n - firsthalf.length();


                    if (w % 2 == 0) {
                        for (int a = 0; a < w / 2; a++)
                            extrascpaces = extrascpaces + " ";
                        firsthalf = extrascpaces + firsthalf + extrascpaces;
                    } else {
                        for (int a = 0; a < (w - 1) / 2; a++)
                            extrascpaces = extrascpaces + " ";
                        firsthalf = extrascpaces + firsthalf + extrascpaces + " ";
                    }

                    inputlist.add(0, firsthalf);
                    break;
                }
            }
        }
        return inputlist;

    }

    //print spaces from 0 until the word while <(n-1)
    public static ArrayList<String> rightJustified(String placeholder) {
        ArrayList<String> inputlist = new ArrayList<String>();
        String secondHalf = "";

        if (indent == 2)
            placeholder = "          " + placeholder;

        if (placeholder.length() < (n)) {
            String padding = "";
            for (int neededSpaces = 0; neededSpaces < (n - placeholder.length()); neededSpaces++)
                padding = padding + " ";
            placeholder = padding + placeholder;
            inputlist.add(placeholder);
            return inputlist;
        } else {
            String extrascpaces = "";
            String firsthalf = "";
            for (int i = (n - 1); i > 0; i--) {
                extrascpaces = extrascpaces + " ";
                if (Character.isWhitespace(placeholder.charAt(i))) {
                    secondHalf = placeholder.substring((i + 1), (placeholder.length()));
                    inputlist = (rightJustified(secondHalf));
                    //the half we want to add
                    //add spaces to justify
                    firsthalf = extrascpaces + placeholder.substring(0, (i));
                    //add it
                    inputlist.add(0, firsthalf);
                    break;
                }
            }
        }
        if (secondHalf == "")
            inputlist.add(placeholder);
        return inputlist;
    }

    public static ArrayList<String> title(String placeholder) {//done for n case


        ArrayList<String> inputlist = new ArrayList<String>();
        String spaces = "";
        int w = n - placeholder.length();

        if (w % 2 == 0) {
            for (int i = 0; i < w / 2; i++)
                spaces = spaces + " ";
            placeholder = spaces + placeholder + spaces;
        } else {
            for (int i = 0; i < (w - 1) / 2; i++)
                spaces = spaces + " ";
            placeholder = spaces + placeholder + spaces + " ";
        }

        inputlist.add(placeholder);
        return inputlist;
    }

    public static ArrayList<String> doubleSpace(String placeholder) {
        ArrayList<String> inputlist = new ArrayList<String>();

        placeholder = placeholder.trim();
        placeholder = placeholder.replaceAll("\\s", "  ");
        //justified =0;//0=left, 1 = center, 2 = right, 3 = title center
        //can be merged with single to amke spaces
        //but adding an if statement above
        if (justified == 0) {
            if (indent == 1)
                placeholder = "     " + placeholder;
            inputlist = leftJustified(placeholder);
        }

        if (justified == 1)
            inputlist = center(placeholder);
        if (justified == 2)
            inputlist = rightJustified(placeholder);
        if (justified == 3)
            title(placeholder);

        return inputlist;
    }

    public static ArrayList<String> singleSpace(String placeholder) {
        ArrayList<String> inputlist = new ArrayList<String>();
        if (justified == 0)
            inputlist = leftJustified(placeholder);
        if (justified == 1)
            inputlist = center(placeholder);
        if (justified == 2)
            inputlist = rightJustified(placeholder);
        if (justified == 3)
            title(placeholder);

        return inputlist;
    }

    //column
    public static void oneColumn(String placeholder) {


        ArrayList<String> inputlist = new ArrayList<String>();
        if (justified == 0)
            inputlist = leftJustified(placeholder);
        if (justified == 1)
            inputlist = center(placeholder);
        if (justified == 2)
            inputlist = rightJustified(placeholder);
        if (justified == 3)
            title(placeholder);

        for (int i = 0; i < inputlist.size(); i++) {
            output.add(inputlist.get(i));
            if (space == 0) {
                if (i % 1 == 0)
                    output.add("");
            }

        }
    }

    public static void twoColumn(String placeholder) {
        String spaces = "          ";
        ArrayList<String> inputlist = new ArrayList<String>();
        if (justified == 0)
            inputlist = leftJustified(placeholder);
        if (justified == 1)
            inputlist = center(placeholder);
        if (justified == 2)
            inputlist = rightJustified(placeholder);
        if (justified == 3)
            title(placeholder);
        //35 10  35

        if (space == 0) {
            String bigspace = "                                             ";
            if ((inputlist.size() % 2) == 0) {
                int counter = (inputlist.size()) / 2;
                for (int i = 0; i < counter; i = i + 2) {
                    output.add(inputlist.get(i) + spaces + inputlist.get(counter + i));
                    output.add("                                                                                ");
                }
            } else {
                int upperhalf = (inputlist.size()) / 2;
                for (int i = 0; i < upperhalf; i = i + 2) {
                    if ((inputlist.size() - i) < 2)
                        output.add(inputlist.get(0) + bigspace);
                    else {
                        output.add(inputlist.get(i) + bigspace);
                        output.add(bigspace + inputlist.get(upperhalf + i));
                    }
                }
            }
        } else {
            if ((inputlist.size() % 2) != 0) {
                int upperhalf = (inputlist.size() + 1) / 2;
                int counter = ((inputlist.size() + 1) / 2) - 1;
                int i = 0;
                for (i = 0; i < counter; i++)
                    output.add(inputlist.get(i) + spaces + inputlist.get(upperhalf + i));
                output.add(inputlist.get(i));
            } else {
                int upperhalf = (inputlist.size()) / 2;
                for (int i = 0; i < upperhalf; i++)
                    output.add(inputlist.get(i) + spaces + inputlist.get(upperhalf + i));
            }
        }
    }

    public static int isCommand(String testThis) {
        /*
         * 1. -l leftJustified
         * 2. -c center
         * 3. -r rightJustified
         * 4. -t title
         *
         * 5. -d doubleSpace
         * 6. -s singleSpace
         *
         * 7. -n removeIndentation
         * 8. -i indentFirst line
         * 9. -b indentMultiple lines
         *
         * 10. -1 1 column
         * 11. -2 2 columns
         *
         * 12. -e blank line
         * */

        if (testThis.equals("-l"))
            return 1;
        if (testThis.equals("-c"))
            return 2;
        if (testThis.equals("-r"))
            return 3;
        if (testThis.equals("-t"))
            return 4;
        if (testThis.equals("-d"))
            return 5;
        if (testThis.equals("-s"))
            return 6;
        if (testThis.equals("-n"))
            return 7;
        if (testThis.equals("-i"))
            return 8;
        if (testThis.equals("-b"))
            return 9;
        if (testThis.equals("-1"))
            return 10;
        if (testThis.equals("-2"))
            return 11;
        if (testThis.equals("-e"))
            return 12;
        return 0;//else
    }
}