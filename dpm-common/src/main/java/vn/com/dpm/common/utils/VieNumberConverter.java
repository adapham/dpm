package vn.com.dpm.common.utils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @author phuong.phamduy
 */
public class VieNumberConverter {

    private static final String KHONG = "không";
    private static final String MOT = "một";
    private static final String HAI = "hai";
    private static final String BA = "ba";
    private static final String BON = "bốn";
    private static final String NAM = "năm";
    private static final String SAU = "sáu";
    private static final String BAY = "bảy";
    private static final String TAM = "tám";
    private static final String CHIN = "chín";
    private static final String LAM = "lăm";
    private static final String LE = "lẻ";
    private static final String MUOI = "mươi";
    private static final String MUOIF = "mười";
    private static final String MOTS = "mốt";
    private static final String TRAM = "trăm";
    private static final String NGHIN = "nghìn";
    private static final String TRIEU = "triệu";
    private static final String TY = "tỷ";
    private static final String[] number = {KHONG, MOT, HAI, BA, BON, NAM, SAU, BAY, TAM, CHIN};


    private VieNumberConverter() {
    }

    public static String getNumberDesc(String number) {
        if (StringUtils.isEmpty(number)) {
            return StringUtils.EMPTY;
        }
        var resultAsArr = readNum(number);
        return toUpperFirstCharacter(String.join(" ", resultAsArr));
    }


    private static List<String> readNum(String a) {
        List<String> kq = new ArrayList<>();

        //Cắt chuổi string chử số ra thành các chuổi nhỏ 3 chữ số
        var listNum = split(a, 3);

        while (!listNum.isEmpty()) {
            //Xét 3 số đầu tiên của chuổi (số đầu tiên của listNum)
            switch (listNum.size() % 3) {
                //3 số đó thuộc hàng trăm
                case 1:
                    kq.addAll(read3Num(listNum.get(0)));
                    break;
                // 3 số đó thuộc hàng nghìn
                case 2:
                    var nghin = read3Num(listNum.get(0));
                    if (!nghin.isEmpty()) {
                        kq.addAll(nghin);
                        kq.add(NGHIN);
                    }
                    break;
                //3 số đó thuộc hàng triệu
                case 0:
                default:
                    var trieu = read3Num(listNum.get(0));
                    if (!trieu.isEmpty()) {
                        kq.addAll(trieu);
                        kq.add(TRIEU);
                    }
                    break;
            }

            //Xét nếu 3 số đó thuộc hàng tỷ
            if (listNum.size() == (listNum.size() / 3) * 3 + 1 && listNum.size() != 1) kq.add(TY);

            //Xóa 3 số đầu tiên để tiếp tục 3 số kế
            listNum.remove(0);
        }


        return kq;
    }

    private static ArrayList<String> read3Num(String a) {
        ArrayList<String> kq = new ArrayList<>();
        int num = ParserUtils.parseInt(a).orElse(-1);
        if (num == 0) return kq;

        int hangTram = ParserUtils.parseInt(a.substring(0, 1)).orElse(-1);
        int hangChuc = ParserUtils.parseInt(a.substring(1, 2)).orElse(-1);
        int hangDv = ParserUtils.parseInt(a.substring(2, 3)).orElse(-1);

        //xét hàng trăm
        if (hangTram != -1) {
            kq.add(number[hangTram]);
            kq.add(TRAM);
        }
        //xét hàng chục
        convertDozens(kq, hangChuc, hangDv);
        //xét hàng đơn vị
        convertUnits(kq, hangChuc, hangDv);

        return kq;
    }

    private static void convertUnits(List<String> kq, int hangChuc, int hangDv) {
        switch (hangDv) {
            case -1:
                break;
            case 1:
                if ((hangChuc != 0) && (hangChuc != 1) && (hangChuc != -1))
                    kq.add(MOTS);
                else kq.add(number[hangDv]);
                break;
            case 5:
                if ((hangChuc != 0) && (hangChuc != -1))
                    kq.add(LAM);
                else kq.add(number[hangDv]);
                break;
            case 0:
                if (kq.isEmpty()) kq.add(number[hangDv]);
                break;
            default:
                kq.add(number[hangDv]);
                break;
        }
    }

    private static void convertDozens(ArrayList<String> kq, int hangChuc, int hangDv) {
        switch (hangChuc) {
            case -1:
                break;
            case 1:
                kq.add(MUOIF);
                break;
            case 0:
                if (hangDv != 0) kq.add(LE);
                break;
            default:
                kq.add(number[hangChuc]);
                kq.add(MUOI);
                break;
        }
    }

    private static ArrayList<String> split(String str, int chunkSize) {
        int du = str.length() % chunkSize;
        //Nếu độ dài chuổi không phải bội số của chunkSize thì thêm # vào trước cho đủ.
        if (du != 0) {
            str = "#".repeat(Math.max(0, (chunkSize - du))) + str;
        }
        return splitStringEvery(str, chunkSize);
    }


    //Hàm cắt chuổi ra thành chuổi nhỏ
    private static ArrayList<String> splitStringEvery(String s, int interval) {
        int arrayLength = (int) Math.ceil(s.length() / (double) interval);
        String[] result = new String[arrayLength];
        int j = 0;
        int lastIndex = result.length - 1;
        for (int i = 0; i < lastIndex; i++) {
            result[i] = s.substring(j, j + interval);
            j += interval;
        }
        result[lastIndex] = s.substring(j);

        /*
          Có thể dùng regex `"(?<=\\G.{" + interval + "})"` để cắt nhưng hiệu suất sẽ thấp hơn cách trên
         */

        return new ArrayList<>(Arrays.asList(result));
    }

    private static String toUpperFirstCharacter(String value) {
        if (StringUtils.isEmpty(value)) {
            return StringUtils.EMPTY;
        } else {
            return String.valueOf(value.charAt(0)).toUpperCase() + value.substring(1);
        }
    }
}
