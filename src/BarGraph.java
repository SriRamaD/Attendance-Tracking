import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Class to depict the data in the Bar chart
 * @author bailoni,nalapat3,rkanduk2,sparvat6,sdonga,spenme11
 *
 */

@SuppressWarnings("serial")
public class BarGraph extends JPanel
{

	/**
	 * Declaring the attributes for parameters of the bar chart
	 * width and height of bars
	 * and the panels
	 */
    private int chartHeight = 340;
    private final int barWidth = 70, barHeight = 10;
    private JPanel barPanel, labelPanel;
    JLabel jLabel;

    private List<BarItem> barRecord = new ArrayList<BarItem>();


    /**
     * Constructor of the Bar chart class
     */
    public BarGraph()
    {
        setBorder(new EmptyBorder(50, 50, 50, 50) );
        setLayout( new BorderLayout() );

        barPanel = new JPanel( new GridLayout(1, 0, barHeight, 0) );
        Border outer = new MatteBorder(1, 1, 1, 1, Color.BLUE);
        Border inner = new EmptyBorder(50, 50, 0, 50);
        Border compound = new CompoundBorder(outer, inner);
        barPanel.setBorder( compound );

        labelPanel = new JPanel( new GridLayout(1, 0, barHeight, 0) );
        labelPanel.setBorder( new EmptyBorder(50, 50, 0, 50) );

        jLabel = new JLabel(" Students ");
        this.add(BorderLayout.WEST,jLabel);


        add(barPanel, BorderLayout.CENTER);
        add(labelPanel, BorderLayout.PAGE_END);
    }


    /**
     * Method to add the columns data of the bar into the list
     * @param label
     * @param value
     * @param color
     *
     */
    public void addBarColumns(String label, int value, Color color)
    {
        BarItem bar = new BarItem(label, value, color);
        barRecord.add( bar );
    }

    /**
     * Modify changes to the layout adding remove panels
     */
    public void BarLayout()
    {
        barPanel.removeAll();
        labelPanel.removeAll();

        int maxValue = 0;

        for (BarItem bar: barRecord)
            maxValue = Math.max(maxValue, bar.getValue());

        for (BarItem bar: barRecord)
        {
            JLabel label = new JLabel(bar.getValue() + "");
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.TOP);
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setVerticalAlignment(JLabel.BOTTOM);
            int barHeight = (bar.getValue() * chartHeight) / maxValue;
            Icon icon = new BarColors(bar.getColor(), barWidth, barHeight);
            label.setIcon( icon );
            barPanel.add( label );

            JLabel barLabel = new JLabel(bar.getBarlabel());
            barLabel.setHorizontalAlignment(JLabel.CENTER);
            labelPanel.add(barLabel);
        }
    }

    /**
     * BarItem Class to hold the Bar Item values
     */
    private class BarItem
    {
        private String barlabel;
        private int value;
        private Color color;

        public BarItem(String label, int value, Color color)
        {
            this.barlabel = label;
            this.value = value;
            this.color = color;
        }

        public String getBarlabel() {
            return barlabel;
        }

        public int getValue()
        {
            return value;
        }

        public Color getColor()
        {
            return color;
        }
    }

    /**
     * Class for colors of the icon in the bar chart
     * @author bailoni,nalapat3,rkanduk2,sparvat6,sdonga,spenme11
     *
     */
    
    private class BarColors implements Icon
    {
        private int shadow = 3;

        private Color color;
        private int width;
        private int height;

        /**
         * Constructor of the BarColors class
         * @param color Color of the bar
         * @param width Width of the bar
         * @param height Height of the bar
         */
        public BarColors(Color color, int width, int height)
        {
            this.color = color;
            this.width = width;
            this.height = height;
        }

        public int getIconHeight()
        {
            return height;
        }
        
        public int getIconWidth()
        {
            return width;
        }


        public void paintIcon(Component com, Graphics graphic, int x, int y)
        {
            graphic.setColor(color);
            graphic.fillRect(x, y, width - shadow, height);
            graphic.setColor(Color.BLACK);
            graphic.fillRect(x + width - shadow, y + shadow, shadow, height - shadow);
        }
    }

}
