/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:12 PM - 12/03/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.authentication_service.utils.helper;

import java.util.List;
import java.util.Random;

public class OtpUtil {
    static Random random = new Random(System.currentTimeMillis());
    public static String generate(int size, OptInclude... includes) {
        var types = List.of(includes);
        if (types.isEmpty() || types.contains(OptInclude.ALL))
            types = List.of(OptInclude.NUMBER, OptInclude.LOWERCASE, OptInclude.UPPERCASE);
        var result = new StringBuilder();
        while (size > 0) {
           OptInclude optInclude = getOptInclude(types);
            result.append(generateChar(optInclude));
            size--;
        }
        return result.toString();
    }

    public static OptInclude getOptInclude(List<OptInclude> includes){
        var type = random.nextInt(OptInclude.values().length);
        while (!includes.contains(OptInclude.getType(type)))
            type = random.nextInt(OptInclude.values().length);
        return OptInclude.getType(type);
    }

    private static char generateChar(OptInclude type) {
        return switch (type) {
            case NUMBER -> (char) (random.nextInt(9) + '0');
            case UPPERCASE -> (char) (random.nextInt(26) + 'A');
            case LOWERCASE -> (char) (random.nextInt(26) + 'a');
            case ALL -> ' ';
        };
    }

    public enum OptInclude {
        NUMBER, UPPERCASE, LOWERCASE, ALL;

        public static OptInclude getType(int type) {
            return switch (type) {
                case 0 -> NUMBER;
                case 1 -> UPPERCASE;
                case 2 -> LOWERCASE;
                default -> ALL;
            };
        }
    }
}
