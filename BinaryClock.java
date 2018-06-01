import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;

/**
 * BinaryClock is a clock that displays the time in binary. It is based on a physical
 * clock that I saw many years ago that displayed the time using lights as binary digits.
 * It had one column of lights for each decimal part part of the time and was read left
 * to right -- H H M M S S. Yellow lights indicated AM; red lights indicated PM. Here is
 * an example using characters:
 *
 * <pre>
 * +------------+
 * |      *   * |
 * |    *       |
 * |  *     *   |
 * |*   *   * * |
 * +------------+
 *  1 2 5 8 3 9
 * </pre>
 *
 * Note: BinaryClock saves its size and location in BinaryClock.properties.
 */
public class BinaryClock extends JFrame {
   private static final String LIGHT_ON = "*";

   private File              propFile = new File("BinaryClock.properties");
   private Properties        props = new Properties();
   private JLabel[]          lights = new JLabel[24];
   private Point             pressLocation;
   private javax.swing.Timer clockTimer;

   // GUI components
   private GridLayout gridLayout1  = new GridLayout();
   private CardLayout cardLayout1  = new CardLayout();
   private JPanel     mainPanel    = new JPanel();
   // private JPopupMenu jMenu1       = new JPopupMenu();
   // private JMenuItem  exitMenuItem = new JMenuItem();

   /**
    * Construct a new instance of this class.
    */
   public BinaryClock() {
      JLabel          label = null;
      FileInputStream propStream = null;

      // Init GUI components.

      jbInit();

      // Add a 4x6 grid of lights.

      // 00 04 08 12 16 20
      // 01 05 09 13 17 21
      // 02 06 10 14 18 22
      // 03 07 11 15 19 23
      // h  h  m  m  s  s

      for (int i = 0; i < 4; i ++) {
         for (int j = 0; j < 6; j ++) {
            int k = i + (j * 4);

            label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setForeground(Color.yellow);
            label.setFont(new Font("Dialog", Font.PLAIN, 16));
            label.setText("");
            mainPanel.add(label);

            lights[k] = label;
         }
      }

      // Load the window settings from the properties file.

      loadProperties();

      // Add window listener for closing window.

      addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent event) {
            saveProperties();
            System.exit(0);
         }
      });

      // Add mouse listeners to handle window movement.

      addMouseListener(new MouseAdapter() {
         public void mousePressed(MouseEvent event) {
            handleMousePressed(event);
         }
      });

      /*
      addMouseMotionListener(new MouseMotionAdapter() {
         public void mouseDragged(MouseEvent event) {
            handleMouseDragged(event);
         }
      });
      */

      // Add listener to "Exit" menu item.
      /*
      exitMenuItem.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent event) {
            saveProperties();
            System.exit(0);
         }
      });
      */

      // Setup and start timer.

      showTime();

      clockTimer = new javax.swing.Timer(
         1000,  // 1000 ms == 1 sec.
         new ActionListener() {
            public void actionPerformed(ActionEvent event) {
               showTime();
            }
         } );
      clockTimer.start();
   }

   /**
    * Init UI components.
    */
   private void jbInit() {
      this.setTitle("BinaryClock");
      this.setSize(new Dimension(195, 160));

      cardLayout1.setHgap(0);
      cardLayout1.setVgap(0);

      this.getContentPane().setLayout(cardLayout1);
      this.getContentPane().setBackground(Color.black);
      this.getContentPane().add(mainPanel, "mainPanel");

      gridLayout1.setColumns(6);
      gridLayout1.setHgap(0);
      gridLayout1.setRows(4);
      gridLayout1.setVgap(0);

      mainPanel.setLayout(gridLayout1);
      mainPanel.setBackground(Color.black);

      /*
      jMenu1.add(exitMenuItem);
      exitMenuItem.setText("Exit");
      */
   }

   /**
    * Move the window to the center of the screen.
    */
   private void centerOnScreen() {
      Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
      setLocation(
         (screenSize.width - getSize().width) / 2,
         ((screenSize.height - getSize().height) / 2) - 25);
   }

   /**
    * Handle mouse pressed event: Save press location and check for popup
    * trigger.
    */
   private void handleMousePressed(MouseEvent event) {
      pressLocation = event.getPoint();

      /*
      if (event.isPopupTrigger()) {
         jMenu1.show(this, 5, 5);
      }
      */
   }

   /**
    * Handle mouse dragged event: Move window.
    */
   /*
   private void handleMouseDragged(MouseEvent event) {
      int   deltaX = event.getX() - pressLocation.x;
      int   deltaY = event.getY() - pressLocation.y;
      Point winLocation = getLocation();

      setLocation(winLocation.x + deltaX, winLocation.y + deltaY);
   }
   */

   /**
    * Load the window properties.  If the properties file does not exist, or
    * reading the file throws an exception, set location to center of screen.
    */
   private void loadProperties() {
      FileInputStream inStream = null;

      if (propFile.exists()) {
         try {
            inStream = new FileInputStream(propFile);

            try {
               props.load(inStream);
               setSize(new Dimension(
                  Integer.parseInt(props.getProperty("w")),
                  Integer.parseInt(props.getProperty("h")) ));
               setLocation(
                  Integer.parseInt(props.getProperty("x")),
                  Integer.parseInt(props.getProperty("y")) );
            } finally {
               inStream.close();
            }
         } catch(Exception ex) {
            System.out.println(ex);
            centerOnScreen();
         }
      } else {
         centerOnScreen();
      }
   }

   /**
    * Save the window properties.
    */
   public void saveProperties() {
      FileOutputStream outStream;
      Point            location = getLocation();
      Dimension        size = getSize();

      props.setProperty("w", Integer.toString(size.width));
      props.setProperty("h", Integer.toString(size.height));
      props.setProperty("x", Integer.toString(location.x));
      props.setProperty("y", Integer.toString(location.y));

      try {
         outStream = new FileOutputStream(propFile);

         try {
            props.store(outStream, null);
         } finally {
            outStream.close();
         }
      } catch(Exception ex) {
         System.out.println(ex);
      }
   }

   /**
    * Show the time.
    */
   private void showTime() {
      Calendar cal = Calendar.getInstance();
      int      ampm = cal.get(Calendar.AM_PM);

      /*
      System.out.println(
         cal.get(Calendar.HOUR) + ":" +
         cal.get(Calendar.MINUTE) + ":" +
         cal.get(Calendar.SECOND) +
         " (" + ampm + ")");
      */

      showTimeField(cal.get(Calendar.HOUR) == 0 ? 12 : cal.get(Calendar.HOUR), ampm, 7);
      showTimeField(cal.get(Calendar.MINUTE), ampm, 15);
      showTimeField(cal.get(Calendar.SECOND), ampm, 23);
   }

   /**
    * Show the given time field value.
    */
   private void showTimeField(int val, int ampm, int index) {
      int digit1 = val / 10;
      int digit2 = val - (digit1 * 10);

      showTimeDigit(digit1, ampm, index - 4);
      showTimeDigit(digit2, ampm, index);
   }

   /**
    * Show the bits for the given time digit value.
    */
   private void showTimeDigit(int val, int ampm, int index) {
      String binStr = Integer.toBinaryString(val);

      for (int i = index, j = binStr.length() - 1; i > (index - 4); i --, j --) {
         if (j >= 0) {
            lights[i].setText(binStr.charAt(j) == '1' ? LIGHT_ON : "");
            lights[i].setForeground(ampm == Calendar.AM ? Color.yellow : Color.red);
         } else {
            lights[i].setText("");
         }
      }
   }

   //--------------------------------------------------------------------------

   /**
    * Start the app.
    */
   public static void main(String[] args) {
      (new BinaryClock()).show();
   }
}
