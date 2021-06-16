/*
デバッグ用クラス

label_levelでラベルの表示を定義できます．

*/

package diary.src;

public final class Debugger {
    // デバッグ

    // ラベルの種類を定義
    public static String label_level_0 = "INF";
    public static String label_level_1 = "WAR";
    public static String label_level_2 = "ERR";
    public static String label_level_3;
    public static String label_level_4;

    // 標準出力
    public static void out(String text) {
        System.out.println(label_level_0 + ": " + text);
    }

    public static void out(String text, String label) {
        if (label.equals(label_level_0))
            System.out.println(label_level_0 + ": " + text);
        else if (label.equals(label_level_1))
            System.out.println(label_level_1 + ": " + text);
        else if (label.equals(label_level_2))
            System.out.println(label_level_2 + ": " + text);
        else if (label.equals(label_level_3))
            System.out.println(label_level_3 + ": " + text);
        else if (label.equals(label_level_4))
            System.out.println(label_level_4 + ": " + text);
        else
            System.out.println("ERR: Did not set label name. (Debugger)");
    }

    public static void out(String text, int label_level) {
        // label_levelは0～4を指定

        if (label_level == 0)
            System.out.println(label_level_0 + ": " + text);
        else if (label_level == 1)
            System.out.println(label_level_1 + ": " + text);
        else if (label_level == 2)
            System.out.println(label_level_2 + ": " + text);
        else if (label_level == 3)
            System.out.println(label_level_3 + ": " + text);
        else if (label_level == 4)
            System.out.println(label_level_4 + ": " + text);
        else
            System.out.println("ERR: Over label_level. (Debugger)");
    }

    // DiaryData内の変数を出力
    public static void debug_out(DiaryData diaryData) {
        String id = String.valueOf(diaryData.getId());
        String year = String.valueOf(diaryData.getYear());
        String month = String.valueOf(diaryData.getMonth());
        String day = String.valueOf(diaryData.getDay());
        String title = diaryData.getTitle();
        String main_text = diaryData.getTitle();
        String flag = String.valueOf(diaryData.getFlag());

        Debugger.out("\n== DEBUG DIARY DATA ==\nid: " + id + "\ndate: " + year + "-" + month + "-" + day + "\ntitle: "
                + title + "\nmain_text: " + main_text + "\nflag: " + flag + "\n");
    }
}
