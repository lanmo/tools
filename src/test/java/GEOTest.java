import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

/**
 * Created by yangnan on 16/10/26.
 */
public class GEOTest {

    public static void main(String[] args) throws UnsupportedEncodingException {

        double a = 116.39065;
        BigDecimal b = new BigDecimal(a);
        System.out.println(a / 2);
        System.out.println(b.divide(new BigDecimal(2)).doubleValue());
        System.out.println(0.06+0.01);

        for (int i=1; i<100000;i++) {

        }

        long start = System.currentTimeMillis();
        double lat = 39.92324;
        double lng = 116.3906;
        String str = toGeoHash(lat, lng, DEFAULT_LEVEL);
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(str);
        start = System.currentTimeMillis();
        for (int i=1;i<10000000;i++) {
            str = toGeoHash(lat, lng, DEFAULT_LEVEL);
        }
        System.out.println(System.currentTimeMillis() - start);
    }

    private static final int DEFAULT_LEVEL = 8;

    /**
     *
     * @param lng 经度
     * @param lat 纬度
     * @param level
     * @return
     */
    public static String toGeoHash(double lat, double lng, int level) {
        int len = level * 5;

        double lngMax = 180;
        double lngMin = -180;
        double latMax = 90;
        double latMin = -90;

        int[] geoBin = new int[len];

        for (int i = 0; i < len; i++) {
            //偶数 经度
            if ((i & 1) == 0) {
                double lngMid = (lngMax + lngMin) / 2;
                if (lng > lngMid) {
                    geoBin[i] = 1;
                    lngMin = lngMid;
                } else {
                    geoBin[i] = 0;
                    lngMax = lngMid;
                }
                //奇数 纬度
            } else {
                double latMid = (latMax + latMin) / 2;
                if (lat > latMid) {
                    geoBin[i] = 1;
                    latMin = latMid;
                } else {
                    geoBin[i] = 0;
                    latMax = latMid;
                }
            }
        }

        return Base32.encode(geoBin);

    }

    private static class Base32 {
        private static final char[] base32Table = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        public static String encode(int[] bin) {

            int len = bin.length;

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < len; i += 5) {

                int idx = (bin[i] << 4)
                        + ((i + 1 < len) ? bin[i + 1] << 3 : 0)
                        + ((i + 2 < len) ? bin[i + 2] << 2 : 0)
                        + ((i + 3 < len) ? bin[i + 3] << 1 : 0)
                        + ((i + 4 < len) ? bin[i + 4] : 0);
                sb.append(base32Table[idx]);
            }

            return sb.toString();
        }
    }
}
