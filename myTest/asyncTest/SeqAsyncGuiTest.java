package asyncTest;

import java.awt.*; 
import java.awt.event.*;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.ArrayList;
import utils.SimulateWork;
import javax.swing.*;
public class SeqAsyncGuiTest extends JFrame implements ActionListener
{
  /**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	ArrayList<Long> eventTriggeringTimeStamp;
    ArrayList<Long> executionStartTimeStamp = new ArrayList<Long>(15);
    ArrayList<Long> executionEndTimeStamp = new ArrayList<Long>();
    int iteration = 10;
    int clickCounter = 0;

  JLabel task1_answer = new JLabel("");
  JLabel task2_answer = new JLabel("");
  JPanel pane = new JPanel(); // create pane object
  JButton task1_button = new JButton("Task1");
  JButton task2_button = new JButton("Task2");
  SeqAsyncGuiTest()   // the constructor
  {
    super("Event Handler Demo"); setBounds(100,100,300,200);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Container con = this.getContentPane(); // inherit main frame
    con.add(pane);
    task1_button.addActionListener(this);   // register button listener
    task2_button.addActionListener(this);   // register button listener
    pane.add(task1_answer); 
    pane.add(task1_button);
    pane.add(task2_answer); 
    pane.add(task2_button);
    setVisible(true); // make frame visible
    System.out.println("In myFrame, running constructor, main thread num" + Thread.currentThread().getId());
    
	this.eventTriggeringTimeStamp =new ArrayList<Long>(this.iteration);
    this.executionStartTimeStamp = new ArrayList<Long>(this.iteration);
    this.executionEndTimeStamp = new ArrayList<Long>(this.iteration);
    
    //Pyjama.omp_register_as_virtual_target("edt");
    //Pyjama.omp_create_virtual_target("worker");

  }

 // here is the basic event handler
  public void actionPerformed(ActionEvent event)
  {
    Object source = event.getSource();
    if (source == task1_button)
    {
//-------------------------------------------------------------------------
    	int myClickID = clickCounter;
    	clickCounter++;
    	System.err.println("handler thread num:" + Thread.currentThread().getId());
    	System.out.println(myClickID + " is starting execution, queuing time:" + ((System.nanoTime()-this.eventTriggeringTimeStamp.get(myClickID)))/10e6);
    	task1_answer.setText("Start Processing Task1!");
    	//long startTime = System.currentTimeMillis();
    	//#omp target virtual(worker) nowait shared(myClickID)
    	{
    		computeTask1();
    		//long timeSpan = System.currentTimeMillis() - startTime;
    		//System.err.println("Task 1 finishes with time span:" + timeSpan);
    		//#omp target virtual(edt)
    		{
    			task1_answer.setText("Task 1 finished");
    		}
    		System.out.println(myClickID + " is finished, flow time:" + ((System.nanoTime()-eventTriggeringTimeStamp.get(myClickID)))/10e6);
    	}

//-------------------------------------------------------------------------
    }
    if (source == task2_button)
    {
    	task2_answer.setText("Start Processing Task2!");
    	System.out.println("Processing task 2");		  
    	System.out.println("Task 2 finished");
    	task2_answer.setText("Task 2 finished");
    }
  }
  
  private int computeTask1() {
	  int i;
	  for(i=1; i<=5; i++) {
		  SimulateWork.work(100);
		  //#omp target virtual(edt) shared(i)
		  {
			  task1_answer.setText("Finish " + i + "/5 of task 1");
		  }
	  }
	  return 1; 
  }
  
  public static void main(String args[]) {
	  	SeqAsyncGuiTest a = new SeqAsyncGuiTest();
        SeqEventPostingThread ept = new SeqEventPostingThread(a, a.iteration, a.eventTriggeringTimeStamp);
        ept.start();
        System.out.println("Done from thread num:" + Thread.currentThread().getId()); 
  }
}