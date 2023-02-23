import java.util.ArrayList;
public class Library {

    public static int BOOK_TITLE_SIZE = 100;
    public static int MAX_SIZE = 66;

    ArrayList<String> BooksToShow = new ArrayList<>();

    public int charToIndex(char c)
    {
        /*
            CHARACTER TO INDEX ENCODING
            a - z => 0 - 25
            A - Z => 26 - 51
            0 - 9 => 52 - 61
            _ => 62 (white space)
            . => 63
            : => 64
            - => 65
         */
        if(c >= 'a' && c <= 'z')
            return c - 97;
        if(c >= 'A' && c <= 'Z')
            return c - 39;
        if(c >= '0' && c <= '9')
            return c + 4;
        if(c == ' ')
            return 62;
        if(c == '.')
            return 63;
        if(c == ':')
            return 64;
        if(c == '-')
            return 65;
        return -1;
    }

    public char indexToChar(int i)
    {
        if(i >= 0 && i <= 25)
            return (char)(i + 97);
        if(i >= 26 && i <= 51)
            return (char)(i + 39);
        if(i >= 52 && i <= 61)
            return (char)(i - 4);
        if(i == 62)
            return ' ';
        if(i == 63)
            return '.';
        if(i == 64)
            return ':';
        if(i == 65)
            return '-';
        return '\0';
    }


    class Book {
        char character;
        boolean isTitle;
        Book[] children;
        String bookCompleteTitle;
        public Book(char character) {
            this.character = character;
            isTitle = false;
            children = new Book[MAX_SIZE];
            bookCompleteTitle = "";
        }
    }
    Book root = new Book('\0'); // the root book

    public void insertBook(String title)
    {
        Book curr = root;
        for(String word : title.split(" ")) {
            for(int i = 0; i < word.length(); i++)
            {
                char c = word.charAt(i);
                if(curr.children[charToIndex(c)] == null)
                {
                    curr.children[charToIndex(c)] = new Book(c);
                }
                curr = curr.children[charToIndex(c)];
            }
            curr.isTitle = true;
            curr.bookCompleteTitle = title;
        }
    }

    private void printBooks(Book book)
    {
        if(book == null)
            return;
        if(book.isTitle)
        {
            BooksToShow.add(book.bookCompleteTitle);
        }
        for(int i = 0; i < MAX_SIZE; i++)
        {
            printBooks(book.children[i]);
        }
    }

    public void debug()
    {
        printBooks(root);
        ArrayList<String> show = removeDuplicates(BooksToShow);
        for(String book : show)
        {
            System.out.printf("Book found: %s\n", book);
        }
    }

    public void printBooks()
    {
        printBooks(root);
    }


    public Book getBook(String title)
    {
        Book curr = root;
        for(String word: title.split(" ")) {
            for(int i = 0; i < word.length(); i++)
            {
                char c = word.charAt(i);
                if(curr.children[charToIndex(c)] == null)
                    return null;
                curr = curr.children[charToIndex(c)];
            }
        }
        return curr;
    }

    public ArrayList<String> removeDuplicates(ArrayList<String> array)
    {
        ArrayList<String> newList = new ArrayList<>();
        for (String item : array) {
            if(!newList.contains(item))
                newList.add(item);
        }
        return newList;
    }

    public void autoComplete(String title)
    {
        if(getBook(title) != null) {
            printBooks(getBook(title));
            ArrayList<String> show = removeDuplicates(BooksToShow);
            for(String book : show)
            {
                System.out.printf("Book found: %s\n", book);
            }
        }
        else
            System.out.printf("No results found for %s.", title);
    }

    public void delete(String title) {
        Book curr = root;
        for(String word: title.split(" ")) {
            for(int i = 0; i < word.length(); i++)
            {
                char c = word.charAt(i);
                if(curr.children[charToIndex(c)] == null)
                    return;
                curr = curr.children[charToIndex(c)];
            }
        }
        curr.isTitle = false;
        curr.bookCompleteTitle = "";
    }
}
