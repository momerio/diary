package diary.src;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
// import java.awt.event.*;

public class EditWindow extends Window {
    // 現在画面の日付
    private String now_year;
    private String now_month;
    private String now_day;

    public final void setNowDate(String year, String month, String day) {
        this.now_year = year;
        this.now_month = month;
        this.now_day = day;
    }

    // ウィンドウの設定
    public EditWindow(String title, int width, int height) {
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 閉じるボタンの処理
        this.setTitle(title); // タイトルの設定
        this.setSize(width, height);// ウィンドウサイズ
        this.setLocationRelativeTo(null);// 画面中央に配置
        this.setResizable(false);// リサイズ禁止
    }

    // ウィンドウのテキスト設定
    public void setTextWindow() {
        final JPanel panel = new JPanel();
        final JLabel label = new JLabel(String.format("%s年 %s月 %s日", this.now_year, this.now_month, this.now_day));

        panel.add(label);

        this.getContentPane().add(panel, BorderLayout.PAGE_START);
    }

    // ウィンドウにテキストエリアを設置
    public void setTextArea(String first_text, int columns) {
        final JTextArea text_area = new JTextArea(first_text); // テキストエリアの初期値を設定
        text_area.setColumns(columns); // テキストエリアの列数を指定
        text_area.setPreferredSize(new Dimension(300, 400)); // サイズを指定
        text_area.setLineWrap(true); // 折り返し指定

        final JPanel panel = new JPanel();
        panel.add(text_area);

        this.getContentPane().add(panel, BorderLayout.CENTER);
    }

    // ウィンドウに枠線カラー指定チェックボックスを設置
    public void setBoxColor() {
        final JCheckBox check1 = new JCheckBox();
        final JCheckBox check2 = new JCheckBox();
        check1.setBorderPainted(true);
        check1.setBorder(new LineBorder(Color.RED, 2, false)); // 枠線の色を指定

        final JPanel p = new JPanel();
        p.add(check1);
        p.add(check2);

        this.getContentPane().add(p, BorderLayout.PAGE_END);
    }

    // ウィンドウを閉じる際の処理
    @Override
    public void dispose() {
        Debugger.out(this.getTitle() + " ウィンドウが閉じられました");
        Setting.open_edit_window_titles.remove(this.getTitle());
        super.dispose();
    }
}
