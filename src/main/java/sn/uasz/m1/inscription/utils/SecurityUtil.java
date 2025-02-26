package sn.uasz.m1.inscription.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtil {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);


    public static String hasherMotDePasse(String mdp) {
        return encoder.encode(mdp);
    }

    public static boolean verifierMotdePasse(String mdp, String hashMdp) {
        return encoder.matches(mdp, hashMdp);
    }

    public static boolean verifierEmailResponsable(String email) {
        if (email == null) return false;

        Matcher matcher = pattern.matcher(email);
        return matcher.matches() && email.endsWith("@univ-zig.sn");
    }

    public static boolean verifierEmailEtudiant(String email) {
        if (email == null) return false;

        Matcher matcher = pattern.matcher(email);
        return matcher.matches() && email.endsWith("@zig.univ.sn");
    }
}
