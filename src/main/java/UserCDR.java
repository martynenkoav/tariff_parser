import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserCDR {
    private String _tariffType;
    private ArrayList<CDR> _callList;
    private Double _totalCost;
    private Integer _totalMinutes;

    public UserCDR(String tariffType){
        _tariffType = tariffType;
        _callList = new ArrayList<CDR>();
        _totalCost = 0.00;
        _totalMinutes = 0;
    }

    public Double getTotalCost() {
        return _totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this._totalCost = totalCost;
    }

    public Integer getTotalMinutes() {
        return _totalMinutes;
    }

    public void setTotalMinutes(Integer totalMinutes) {
        this._totalMinutes = totalMinutes;
    }

    public String getTariffType() {
        return _tariffType;
    }

    public void setTariffType(String tariffType) {
        this._tariffType = tariffType;
    }

    public ArrayList<CDR> getCallList() {
        return _callList;
    }

    public void setCallList(ArrayList<CDR> _callList) {
        this._callList = _callList;
    }

    public void addCdr(CDR cdr){
        this._callList.add(cdr);
    }

    public void minutesAndCost(CDR cdr){
        _totalCost += cdr.getCost();
        _totalMinutes += cdr.getDurationInMin();
    }

    public void sortCallsList(){
        Collections.sort(_callList, Comparator.comparing(CDR::getBeginning));
    }

    public void count_tariff() {
        switch (_tariffType) {
            case ("06"):
                _callList.forEach((call) -> {
                    if (_totalMinutes + call.getDurationInMin() > 300) {
                        if (_totalMinutes > 300) {
                            call.setCost(Double.valueOf(call.getDurationInMin()));
                            minutesAndCost(call);
                        } else {
                            call.setCost((double) (_totalMinutes + call.getDurationInMin() - 300));
                            minutesAndCost(call);
                        }
                    } else {
                        minutesAndCost(call);
                    }
                });
                break;
            case ("03"):
                _callList.forEach((call) -> {
                    call.setCost(call.getDurationInMin() * 1.5);
                    minutesAndCost(call);
                });
                break;
            case ("11"):
                _callList.forEach((call) -> {
                    if (call.isOutgoingCall()) {
                        if (_totalMinutes + call.getDurationInMin() > 100) {
                            if (_totalMinutes > 100) {
                                call.setCost((call.getDurationInMin()) * 1.5);
                                minutesAndCost(call);
                            } else {
                                call.setCost((_totalMinutes + call.getDurationInMin() - 100) * 1.5 + 100 * 0.5);
                                minutesAndCost(call);
                            }
                        } else {
                            call.setCost(call.getDurationInMin() * 0.5);
                            minutesAndCost(call);
                        }
                    }
                });
                break;
            default:
                break;
        }
    }

}

