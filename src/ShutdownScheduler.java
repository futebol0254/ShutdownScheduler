import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ShutdownScheduler extends JFrame {

    private final JRadioButton byDateTimeRadio = new JRadioButton("Por horário (data/hora);") ;
    private final JRadioButton byCountdownRadio = new JRadioButton("Por contagem (minutos).");

    private final JSpinner dateTimeSpinner;
    private final JSpinner minutesSpinner;

    private final JButton scheduleBtn = new JButton("Agendar");
    private final JButton canncelBtn = new JButton("Cancelar");
    private final JButton shutdownNowBtn = new JButton("Desligar agora");
    private final JLabel statusLabel = new JLabel("Status: pronto!");

    private final Timer uiTimer;
    private Long scheduledEpochMillis = null;

    public ShutdownScheduler() {
        super("Agendador de Desligamento");

        if (!isWindows()) {
            JOptionPane.showMessageDialog(this,
                    "Este ´programa roda no windows no momento",
                    "Seu sistema não é suportado", JOptionPane
                            .WARNING_MESSAGE
            );
        }

        Date initial = new Date(System.currentTimeMillis() + 10 * 60 * 1000L);
        SpinnerDateModel dateModel = new SpinnerDateModel(initial,null, null, Calendar.MINUTE);
        dateTimeSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateTimeSpinner, "dd/MM//yyyHH:mm:ss");
        dateTimeSpinner.setEditor(dateEditor);
        SpinnerNumberModel minutesModel = new SpinnerNumberModel(15, 1,5256000,1);
        minutesSpinner = new JSpinner(minutesModel);
        ButtonGroup group = new ButtonGroup();
        group.add(byDateTimeRadio);
        group.add(byCountdownRadio);
        JPanel modelPanel = new JPanel(new GridLayout(0,1,8,
                8));
                modelPanel.setBorder(new TitledBorder("Modo de agendamento"));
                modelPanel.add(byDateTimeRadio);
                modelPanel.add(byCountdownRadio);
                JPanel paramsPanel = new JPanel(new GridLayout());
                paramsPanel.setBorder(new TitledBorder("Parâmetros"));
                GridBagConstraints c = new GridBagConstraints();
                c.insets = new Insets(6,6,6,6);
                c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.LINE_END;
                paramsPanel.add(new JLabel("Data/Hora"),c);
                c.gridy = 1; c.anchor = GridBagConstraints.LINE_START;
                paramsPanel.add(dateTimeSpinner, c);

        uiTimer = new Timer(1000, e-> System.out.println("Teste"));
        uiTimer.start();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(560, 240);
        setLocationRelativeTo(null);
    }
    private boolean isWindows(){
        String os = System.getProperty("os.name", "").toLowerCase();
        return os.contains("win");
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ShutdownScheduler().setVisible(true);
        });
    }

}