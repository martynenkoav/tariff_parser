import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
    в данном классе задаем формат вывода отчета
 */

public class Report {

    public static void makeReport(String phone_number, UserCDR userCDR){
        try(FileWriter writer = new FileWriter("src/reports/report_" + phone_number, false))
        {
            writer.write("Tariff index: " + userCDR.getTariffType() + "\n");
            writer.write("----------------------------------------------------------------------------\n");
            writer.write("For phone number " + phone_number + "\n");
            writer.write("----------------------------------------------------------------------------\n");
            writer.write("| Call Type |   Start Time        |     End Time        | Duration | Cost  |\n");
            writer.write("----------------------------------------------------------------------------\n");
            ArrayList<CDR> cur_call_list = userCDR.getCallList();
            for (int i = 0; i < cur_call_list.size(); i++){
                CDR cur_cdr = cur_call_list.get(i);
                writer.write("|     " + cur_cdr.getCallType() + "    | " + FormatData.formattedDate(cur_cdr.getBeginning()) +
                        " | " + FormatData.formattedDate(cur_cdr.getEnding()) + " | " +
                        FormatData.secToStr(cur_cdr.getDurationInSec())+ " | " + FormatData.formatCost(cur_cdr.getCost())+ "  |\n");
            }
            writer.write("----------------------------------------------------------------------------\n");
            writer.write("|                                           Total Cost: |     " + FormatData.formatCost(userCDR.getTotalCost()) + " rubles |\n");
            writer.write("----------------------------------------------------------------------------\n");
        } catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
