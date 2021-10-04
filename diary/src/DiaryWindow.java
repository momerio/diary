/*
 * ウィンドウの設定
*/
package diary.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

public class DiaryWindow extends Window implements ActionListener {
    // 現在画面の日付
    protected String now_year;
    protected String now_month;
    protected String now_day;

    // 年月遷移ボタン類
    private final JButton prev_month = new JButton("翌月");// 翌月ボタン
    private final JButton back_month = new JButton("前月");// 前月ボタン
    private final JButton prev_year = new JButton("翌年");// 翌年ボタン
    private final JButton back_year = new JButton("前年");// 前年ボタン

    private JLabel YM_label;// 年月表示ラベル

    // day panel
    private JPanel day_panel = new JPanel();

    public final void setNowDate(String year, String month, String day) {
        this.now_year = year;
        this.now_month = month;
        this.now_day = day;
    }

    // ウィンドウの設定
    public DiaryWindow(String title, int width, int height) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); // 閉じるボタンの処理
        this.setTitle(title); // タイトルの設定
        this.setSize(width, height);// ウィンドウサイズ
        this.setLocationRelativeTo(null);// 画面中央に配置
        this.setResizable(false);// リサイズ禁止
    }

    // ウィンドウのテキスト設定
    public void setTextWindow() {
        final JPanel panel = new JPanel(); // パネルのインスタンスの 生 成

        // 前年ボタン
        // final JButton backYear = new JButton("前年");
        this.back_year.addActionListener(this);
        panel.add(this.back_year);

        // 前月ボタン
        // final JButton backMonth = new JButton("前月");
        this.back_month.addActionListener(this);
        panel.add(this.back_month);

        // 〇〇年✕✕月
        this.YM_label = new JLabel(this.now_year + "年 " + this.now_month + "月"); // ラベルのインスタンスの生成
        this.YM_label.setFont(new Font("MSGothic", Font.PLAIN, 30));
        this.YM_label.setForeground(Color.BLUE);
        panel.add(YM_label);

        // 翌月ボタン
        // final JButton prevMonth = new JButton("翌月");
        this.prev_month.addActionListener(this);
        panel.add(this.prev_month);

        // 翌年ボタン
        // final JButton prevYear = new JButton("前年");
        this.prev_year.addActionListener(this);
        panel.add(this.prev_year);

        this.add(panel, BorderLayout.NORTH); // パネルをウインドウの表示領域に配置
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.back_month) {
            // 前月ボタンの処理
            final int temp_month = Integer.valueOf(this.now_month) - 1;
            if (1 <= temp_month && temp_month <= 12) {
                this.now_month = String.valueOf(temp_month);
            } else if (temp_month <= 0) {
                this.now_month = "12";
                this.now_year = String.valueOf(Integer.valueOf(this.now_year) - 1);
            }
            this.setButtonWindow(); // その年月の日付ボタンを設定
            this.YM_label.setText(this.now_year + "年 " + this.now_month + "月");
        } else if (e.getSource() == this.prev_month) {
            // 翌月ボタンの処理
            final int temp_month = Integer.valueOf(this.now_month) + 1;
            if (1 <= temp_month && temp_month <= 12) {
                this.now_month = String.valueOf(temp_month);
            } else if (13 <= temp_month) {
                this.now_month = "1";
                this.now_year = String.valueOf(Integer.valueOf(this.now_year) + 1);
            }
            this.setButtonWindow(); // その年月の日付ボタンを設定
            this.YM_label.setText(this.now_year + "年 " + this.now_month + "月");
        } else if (e.getSource() == this.back_year) {
            // 前年ボタンの処理
            final int temp_year = Integer.valueOf(this.now_year) - 1;
            if (temp_year < 1950) { // 例外チェック
                // メッセージダイアログの表示
                JOptionPane.showMessageDialog(null, "ごめんなさい^_^\n1950年よりも前には遡れません。");
            } else {
                this.now_year = String.valueOf(Integer.valueOf(this.now_year) - 1);
                this.setButtonWindow(); // その年月の日付ボタンを設定
                this.YM_label.setText(this.now_year + "年 " + this.now_month + "月");
            }
        } else if (e.getSource() == this.prev_year) {
            // 翌年ボタンの処理
            final int temp_year = Integer.valueOf(this.now_year) + 1;
            if (2200 < temp_year) {
                // メッセージダイアログの表示
                JOptionPane.showMessageDialog(null, "ごめんなさい^_^\n2200年よりも後には進めません。");
            } else {
                this.now_year = String.valueOf(Integer.valueOf(this.now_year) + 1);
                this.setButtonWindow(); // その年月の日付ボタンを設定
                this.YM_label.setText(this.now_year + "年 " + this.now_month + "月");
            }
        }

    }

    // ウィンドウのボタン設定
    public void setButtonWindow() {
        // JPanel panelAction = new JPanel();

        // ボタンを追加
        // JButton btn1 = new JButton("Push");

        // ボタンクリック
        // btn1.addActionListener(new CreateEditWindow());

        // パネルに追加
        // panelAction.add(btn1);

        // その月の日数を取得
        // this.nowMonth = "2"; //debug
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(this.now_year), Integer.parseInt(this.now_month) - 1, 1);
        final int day_in_month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        final int grid_row = 7;
        final int grid_col = 5;
        final GridLayout gridDate = new GridLayout(0, grid_row, 20, 20); // 行 列 横 縦

        // final JPanel day_panel = new JPanel();
        day_panel.removeAll(); // 一旦パネルの要素を全部削除
        day_panel.setLayout(gridDate);
        for (int i = 0; i < grid_row * grid_col; i++) {
            if (i < day_in_month) {
                final JButton dateButton = new JButton("" + (i + 1));
                // dateButton.setBackground(Color.LIGHT_GRAY);// ボタン背景色
                dateButton
                        .addActionListener(new CreateEditWindow(this.now_year, this.now_month, String.valueOf(i + 1)));
                day_panel.add(dateButton);
            } else { // 最大日付外 -> 枠だけ表示
                final JButton dateButton = new JButton();
                dateButton.setEnabled(false);
                day_panel.add(dateButton);
            }
        }
        Debugger.out("hgoe; " + this.now_year + "年" + this.now_month + "月" + this.now_day + "日");

        // ボタンを表示
        this.getContentPane().add(day_panel, BorderLayout.CENTER);
        // this.getContentPane().add(panelAction, BorderLayout.SOUTH);
    }

    // 日付ボタンクリック時のアクション
    static class CreateEditWindow implements ActionListener {

        private String now_year;
        private String now_month;
        private String now_day;

        public CreateEditWindow(String year, String month, String day) {
            this.now_year = year;
            this.now_month = month;
            this.now_day = day;
        }

        // 同一タイトルのウィンドウが既に開かれているか確認
        private boolean isOpened(String title) {
            return Setting.open_edit_window_titles.contains(title);
        }

        // 開いている編集ウィンドウリストに追加
        private void addOpenedWindow(String title) {
            Setting.open_edit_window_titles.add(title);
        }

        // 新しい画面の作成
        public void actionPerformed(ActionEvent e) {

            // ウィンドウ設定
            final String window_title = String.format("%s年%s月%s日の日記", this.now_year, this.now_month, this.now_day); // タイトル
            Debugger.out("CreateEditWindow of " + window_title);

            // 同一ウィンドウタイトルで既に開かれているか確認
            if (this.isOpened(window_title)) {
                // 既に開かれている場合 メッセージを表示
                JOptionPane.showMessageDialog(null, "同じウィンドウは開けません！\n " + window_title + "の日記は既に開いています。");
            } else {
                // 開いていない場合

                // 開いているウィンドウに現在のウィンドウタイトルを追加
                this.addOpenedWindow(window_title);

                // ウィンドウの設定
                final int window_width = 400; // 高さ
                final int window_height = 400; // 幅
                final EditWindow editWindow = new EditWindow(window_title, window_width, window_height);

                // 日付
                editWindow.setNowDate(this.now_year, this.now_month, this.now_day);
                Debugger.out(this.now_year + "年" + this.now_month + "月" + this.now_day + "日");

                // アイコン
                editWindow.setImageIcon(Setting.icon_path);

                editWindow.setTextWindow(); // ウィンドウにテキストを表示
                editWindow.setTitle(window_title); // タイトルを設定
                editWindow.setTextArea("", 15); // ウィンドウにテキストエリアを設置
                editWindow.setBoxColor(); // ウィンドウに枠線カラー指定チェックボックスを設置

                editWindow.setVisible(); // 最後にウィンドウを表示
            }

        }
    }

}
