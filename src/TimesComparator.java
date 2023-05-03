import java.time.LocalTime;
import java.util.Comparator;

public class TimesComparator implements Comparator<LocalTime>{
    @Override
    public int compare(LocalTime time1,LocalTime time2){
        return time1.compareTo(time2);
    }
}
