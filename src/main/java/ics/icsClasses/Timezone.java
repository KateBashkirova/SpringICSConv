package ics.icsClasses;

//меняем формат таймзоны на понятный календарю
public class Timezone {

    //расшифровка кода таймзоны
    public String tzid(String timezone) {
        //tzid - это "пояснительная бригада" на случай, если код таймзоны ни о чём тебе не говорит
        String tzid = timezone.replaceAll("[\\s0-9:+]", ""); //удаляем все цифры, знаки и пробелы
        return tzid;
    }

    //смещение относительно UTC
    public String tzOffSet(String timezone){
        //таймзона всегда имеет вид знакчч:чч. Для tzOffSet нужно удалить всё, кроме цифр и знака
        String tzOffSet = timezone.replaceAll("[\\sa-zA-Z:\\/]", "");
        return tzOffSet;
    }

    //две первые цифры кода таймзоны
    public String tzName(String timezone){
        String fullTzCode = tzOffSet(timezone);
        return fullTzCode.substring(0,3); //берём только знак и первые 2 цифры
    }
}
