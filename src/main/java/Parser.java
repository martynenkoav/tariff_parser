import java.io.*;
import java.text.ParseException;
import java.util.HashMap;

/*
    Класс необходимый для парсинга файла с отчетами по звонкам
*/

public class Parser {

    public static HashMap<String, UserCDR> parse(String filePath) throws IOException, ParseException {
        HashMap<String, UserCDR> map = new HashMap<String, UserCDR>();
        File file = new File(filePath);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;

        while((line = br.readLine()) != null){
            line = line.replaceAll("\\s", "");
            line = line.replaceAll(",", " ");
            String[] words = line.split(" ");
            if(!map.containsKey(words[1])) {
                map.put(words[1], new UserCDR(words[4]));
            }
            CDR cur_cdr = new CDR(words[0], FormatData.convert(words[2]), FormatData.convert(words[3]));
            cur_cdr.setDuration();
            map.get(words[1]).addCdr(cur_cdr);
        }
        br.close();
        map.forEach((k, v) -> v.sortCallsList());
        return map;
    }
}
