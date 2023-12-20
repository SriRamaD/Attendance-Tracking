import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
//import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
//import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

/**
 * GUI class with a GUI consisting of menu bar with File and About options.
 * @author bailoni,nalapat3,rkanduk2,sparvat6,sdonga,spenme11
 *
 */

public class GUI {
	
	Long miniTime = Long.MIN_VALUE;
	Long maxTime = Long.MAX_VALUE;

	JFrame frame = new JFrame("CSE 563 Final Project");
	//Variables to store data attendance
	private ArrayList<Student> studentList = new ArrayList<>(); 
	private ArrayList<Attendance> att1 = new ArrayList<>(); 
	private ArrayList<Attendance> att2 = new ArrayList<>(); 

	String extraDialogInfo = "";

	//Table and Scroll pane creation
	JTable table;
	DefaultTableModel tableModel;
	JScrollPane scrollPane;

	//FileTime date0;
	//Creation dates for the file uploads
	String date0 = "",date1 = "",date2 = "";
	int studentsPresent1 = 0;
	int studentsPresent2 = 0;

	/**
	 * Constructor of the GUI class
	 */
	 public GUI() {
		 
		 table = new JTable();
	     frame.setLayout(new BorderLayout());
	        
	     /**
	      * Creating GUI with JMenuBar, 
	      * JMenu: File,About
	      * and multiple JMenuItem
	      */
	     JMenuBar menuBar =new JMenuBar();
	     JMenu file = new JMenu("File");
	     JMenu about = new JMenu("About");
	     menuBar.add(file);
	     menuBar.add(about);
	     frame.setJMenuBar(menuBar);
	     JMenuItem load =new JMenuItem("Load a Roaster");
	     JMenuItem add =new JMenuItem("Add Attendance");
	     JMenuItem save =new JMenuItem("Save");
	     JMenuItem plot =new JMenuItem("Plot Data");
	     JMenuItem aboutInfo = new JMenuItem("About Team");
	     file.add(load);
	     file.add(add);
	     file.add(save);
	     file.add(plot);
	     about.add(aboutInfo);
	        
	        /**
	         * Action to be performed when About is selected
	         */
	        aboutInfo.addActionListener(new ActionListener() {
	        	@Override
	        	public void actionPerformed(ActionEvent e) {
	        	    tableModel = new DefaultTableModel();
	        	    table = new JTable(tableModel);
                    tableModel.addColumn("Team Members");
                    tableModel.addColumn("ASURITE ID");
                    tableModel.addRow(new Object[] {"Basanth Reddy Ailoni","bailoni"});
                    tableModel.addRow(new Object[] {"Nikhil Alapati","nalapat3"});
                    tableModel.addRow(new Object[] {"Rajshree Kandukuri","rkanduk2"});
                    tableModel.addRow(new Object[] {"Sai Chandra Kaushik Reddy Parvatala","sparvat6"});
                    tableModel.addRow(new Object[] {"Sri Rama Naga Venkata Donga","sdonga"});
                    tableModel.addRow(new Object[] {"Srivalli Penmetsa","spenme11"});
	   
                    table.setBounds(0, 65, 432, 188);
                    table.setModel(tableModel);
                    scrollPane = new JScrollPane(table);
                    scrollPane.setPreferredSize(new Dimension(500, 500));
                    frame.add(BorderLayout.CENTER, scrollPane);
                    frame.revalidate();
                    frame.repaint();
	        	}
	        });
	        
	        
	        /**
	         * Action to be performed when 'Load a Roaster' option is selected
	         */
	        
	        load.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {

	                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	                int result = fileChooser.showOpenDialog(frame);
	                if (result == JFileChooser.APPROVE_OPTION) 
	                {
	                    File selected = fileChooser.getSelectedFile();
	                    //Adding information from the file present in the specified path
	                    studentList = loadfile(selected.getAbsolutePath());

	                    tableModel = new DefaultTableModel();
	                    tableModel.addColumn("ID");
	                    tableModel.addColumn("First Name");
	                    tableModel.addColumn("Last Name");
	                    tableModel.addColumn("ASURITE");
                        int listData = 0;
	                    while(listData < studentList.size()) 
	                    {
	                        tableModel.addRow(new Object[]{studentList.get(listData).getID(), 
	                        		studentList.get(listData).getFirstName(), studentList.get(listData).getLastName(), studentList.get(listData).getASURITE()});
	                        listData++;
	                    }

	                    table.setBounds(30, 40, 200, 300);
	                    table.setModel(tableModel);

	                    scrollPane = new JScrollPane(table);
	                    scrollPane.setPreferredSize(new Dimension(500, 500));
	                    frame.add(BorderLayout.CENTER, scrollPane);

	                    frame.revalidate();
	                    frame.repaint();
	                }

	            }
	        });
	        
	        
	        /**
	         * Action performed when 'Add Attendance' option is selected.
	         */

	        add.addActionListener(new ActionListener() {
	            @Override
	            @Deprecated
	            public void actionPerformed(ActionEvent e) {

	                //Choosing file from the file path specified (absolute)
	                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	                int result = fileChooser.showOpenDialog(frame);

	                if (result == JFileChooser.APPROVE_OPTION) {
	                    File selectedFile = fileChooser.getSelectedFile();
	                    if(date1.isEmpty())
	                        att1 = loadAttendace(selectedFile.getAbsolutePath()); 
	                    else
	                        att2 = loadAttendace(selectedFile.getAbsolutePath()); 

	                    Path filePath = selectedFile.toPath();
	                    
	                    //Process to get the date of the file
	                    //Reference: https://stackoverflow.com/questions/2723838/determine-file-creation-date-in-java
	                    BasicFileAttributes attr = null;
	                    try {
	                        attr = Files.readAttributes(filePath, BasicFileAttributes.class);
	                    } catch (IOException e2) {
	                    	System.out.println("Exception while getting file" + e2.getMessage());
	                    }

	                    long ms = attr.creationTime().to(TimeUnit.MILLISECONDS);
	                    if((ms > miniTime) && (ms < maxTime))
	                    {
	                        //file date
	                        Date creationDate = new Date(attr.creationTime().to(TimeUnit.MILLISECONDS));

	                        if(date1.isEmpty() && date2.isEmpty())
	                            date1 = ""+ creationDate.getDate() + "/" + (creationDate.getMonth() + 1) + "/" + (creationDate.getYear() + 1900);
	                        else
	                            date2 = ""+ creationDate.getDate() + "/" + (creationDate.getMonth() + 1) + "/" + (creationDate.getYear() + 1900);

	                    }

	                    tableModel.setRowCount(0);
	                    if(!date1.trim().isEmpty() && date2.trim().isEmpty()) {
	                        tableModel.addColumn(date1);
	                    }
	                    if(!date2.trim().isEmpty()) {
	                        tableModel.addColumn(date2);
	                    }

	                    int count = 0, extra=0;

	                    if(!date1.trim().isEmpty() && date2.isEmpty())
	                    {
	                    	int i =0;
	                        while ( i < att1.size()) {
	                       String asurite = findASURite(att1.get(i).getASURITE(), att1.get(i).getTimes());
	                                if (asurite!=null) {
	                                    count++;
	                                } else {
	                                    extra++;
	                                }
	                                i++;
	                        }
	                        studentsPresent1 = count;
	                    }

	                    if(att2.size()>0)
	                    {
	                    	int i = 0;
	                        while ( i < att2.size()) {
	                            String asurite = findASURite(att2.get(i).getASURITE(), att1.get(i).getTimes());
	                            if (asurite!=null) {
	                                count++;
	                            } else {
	                                extra++;
	                            }
	                            i++;
	                        }
	                        studentsPresent2 = count;
	                    }
	                    
	                    //Adding Rows
	                    //Reference: https://stackoverflow.com/questions/3549206/how-to-add-row-in-jtable
	                    int j = 0;
	                    while ( j < studentList.size()) {
	                        if (att1.get(j).getTimes() != 0) {
	                            if(!date1.isEmpty() && !date2.isEmpty()) {
	                                tableModel.addRow(new Object[]{studentList.get(j).getID(), 
	                                		studentList.get(j).getFirstName(), studentList.get(j).getLastName(), 
	                                		studentList.get(j).getASURITE(), att1.get(j).getTimes(), att2.get(j).getTimes()});
	                            }else
	                            {
	                                tableModel.addRow(new Object[]{studentList.get(j).getID(), studentList.get(j).getFirstName(), 
	                                		studentList.get(j).getLastName(), studentList.get(j).getASURITE(), att1.get(j).getTimes()});
	                            }
	                        }
	                        j++;
	                    }

	                    //Code to get additional number of times from att1
	                    
	                    if(att1.size() > 0)
	                    {
	                    	int i = 0;
	                         while(i < att1.size()) {
	                            String asurite = findASURite(att1.get(i).getASURITE(), att1.get(i).getTimes());
	                            if (asurite == null && att1.get(i).getTimes() == 1) {
	                                extraDialogInfo = att1.get(i).getASURITE() + ", connected for " + att1.get(i).getTimes() +"\n";
	                            }
	                            else continue;
	                            i++;
	                        }
	                    }

	                    //Code to get additional number of times from att2
	                    
	                    if(att2.size() > 0)
	                    {
	                    	int i = 0;
	                        while( i < att2.size()) 
	                        {
	                            String asurite = findASURite(att2.get(i).getASURITE(), att2.get(i).getTimes());
	                            if (asurite == null && att1.get(i).getTimes() == 1) {
	                                extraDialogInfo = att2.get(i).getASURITE() + ", connected for " + att2.get(i).getTimes() +"\n";
	                            }
	                            else continue;
	                            i++;
	                        }
	                    }


	                    table.setModel(tableModel);
	                    
	                    //Generating dialog box to the user that the user is not present in the roaster.
	                    
	                    JOptionPane.showMessageDialog(frame, "Data loaded for " + count + " users in the roaster.\n" +
	                              extra +" Additional "+ " attendee was found:\n"+
	                            extraDialogInfo);

	                    frame.repaint();
	                    frame.revalidate();
	                }

	            }
	        });
	        
	        /**
	         * Action Performed when 'Save' option is selected.
	         */

	        save.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                saveFile();

	            }
	            
	        });
	        
	        /**
	         * Action performed when 'Plot Data' option is clicked.
	         */

	        plot.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {

	                frame.getContentPane().remove(scrollPane);            

	                BarGraph panel = new BarGraph();
	                panel.addBarColumns(date1, studentList.size(), Color.BLUE);
	                panel.addBarColumns(date1, studentsPresent1, Color.BLUE);
	                panel.addBarColumns(date2, studentsPresent2, Color.BLUE);

	                panel.BarLayout();

	                JPanel jPanel = new JPanel();
	                jPanel.setAlignmentX(350);
	                jPanel.add(new JLabel(" Dates "));
	                
	                frame.add(BorderLayout.SOUTH, jPanel);


	                frame.add(BorderLayout.CENTER,panel);
	                frame.revalidate();
	                frame.repaint();
	            }
	        });

	        frame.setSize(new Dimension(900,900));
	        frame.setLocationRelativeTo(null);
	        frame.setVisible(true);
	    }

	    /**
	     * Method to find the ASURite ID of the student
	     * @param asurite, Input the ASURite ID of the student
	     * @param times, Input the number of times the student attended the class
	     * @return string
	     */
	    private String findASURite(String asurite, int times) {

	        for (int i = 0; i < studentList.size(); i++) 
	        {
	                if(studentList.get(i).getASURITE().equalsIgnoreCase(asurite))
	                {
	                    studentList.get(i).setTime(times);
	                    return asurite;
	                }
	                else
	                {
	                    continue;
	                }
	        }

	        return null;
	    }

	    /**
	     * Method to save the data into file when 'Save' option is selected.
	     */
	    
	    private void saveFile() {

	        try {

	            File file = new File("studentData.csv");
	            if (!file.exists()) 
	            	file.createNewFile();

	            if(studentList.size() > 0) {
	                FileWriter fileWriter = new FileWriter(file);
	                fileWriter.write("ID,First Name,Last Name, ASURITE," + date1 + "," + date2 + "\n\n");
	                int i = 0;
	                 while( i < studentList.size()) 
	                {
	                    fileWriter.append(""+studentList.get(i).getID() + "," 
	                + studentList.get(i).getFirstName() + "," + studentList.get(i).getLastName() + "," 
	                    		+ studentList.get(i).getASURITE() + "," + att1.get(i).getTimes() + "," + att2.get(i).getTimes() + "\n");
	                    i++;
	                }
	                fileWriter.close();
	            }

	        }catch (Exception e4)
	        {
	            System.out.println("Expection handled"+ e4.getMessage());
	        }

	    }

	    /**
	     * Method to read the attendance from the file and load into a list
	     * @param absolutePath The path where the file exists
	     * @return list The list with loaded attendance data
	     */
	    private ArrayList<Attendance> loadAttendace(String absolutePath) {

	        ArrayList<Attendance> list = new ArrayList<>();

	        try
	        {
	            Scanner sc = new Scanner(new File(absolutePath));
	            while(sc.hasNextLine())
	            {
	                String[] data  = sc.nextLine().split(","); //split with ,
	                list.add(new Attendance(data[0],Integer.parseInt(data[1])));
	            }

	        }
	        catch (Exception e5)
	        {
	            System.out.println("Exception handled"+e5.getMessage());
	        }

	        return list;
	    }

	    /**
	     * Method to read the file and store the data in a list
	     * @param path Path of the file
	     * @return list Returns the list with the data of the file loaded into it.
	     */
	    private ArrayList<Student> loadfile(String path) {

	        ArrayList<Student> list = new ArrayList<>();

	        try
	        {
	            // Read the file in the specified path
	            Scanner sc = new Scanner(new File(path));
	            //Split and add the data
	            while(sc.hasNextLine())
	            {
	                String[] data  = sc.nextLine().split(",");
	                list.add(new Student(data[0],data[1],data[2],data[3]));
	            }

	        }
	        catch (Exception e6)
	        {
	        	System.out.println("Exception handeled"+e6.getMessage());
	        }
	        

	        return list;
	    }
	    
	    // Launching GUI
	    
	    public void launch()
	    {
	    	new GUI();
	    }
}

