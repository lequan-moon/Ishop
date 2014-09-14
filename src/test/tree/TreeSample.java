package test.tree;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import com.nn.ishop.client.gui.components.CProductCategoryGroupTreeNode;
import com.nn.ishop.client.gui.components.CTreeCellRenderer;
import com.nn.ishop.client.gui.components.CProductCategoryTreeNode;
import com.nn.ishop.client.gui.components.CProductSectionTreeNode;


public class TreeSample {
	  public static void main(String args[]) {
		    JFrame f = new JFrame("JTree Sample");
		    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		    JPanel pnlMain = new JPanel(new BorderLayout());
		    pnlMain.setBackground(Color.white);

		    createTree(pnlMain);

		    f.setContentPane(pnlMain);

		    f.setSize(300, 200);
		    f.setVisible(true);
		  }

		  private static void createTree(JPanel pnlMain) {
		    Employee bigBoss = new Employee(Employee.randomName() + "-level1", true/*is a boss*/);
		    Employee[] level1 = new Employee[3];    
		    bigBoss.employees = level1;

		    for (int i = 0; i < level1.length; i++) {
		      level1[i] = new Employee(Employee.randomName() + "-level2", true/*is a boss*/);      
		    }

		    for (int i = 0; i < level1.length; i++) {
		      Employee employee = level1[i];
		      if (employee.isBoss) {
		        int count = 3;
		        employee.employees = new Employee[count];

		        for (int j = 0; j < employee.employees.length; j++) {
		          employee.employees[j] = new Employee(Employee.randomName()+"-level3", false/*is not a boss*/);          
		        }
		      }
		    }

		    CProductSectionTreeNode root = new CProductSectionTreeNode(bigBoss);           
		    DefaultTreeModel model = new DefaultTreeModel(root);

		    for (Employee employee : bigBoss.employees) {
		    	System.out.println(employee.name + " -- is boss -- " + employee.isBoss);
		      CProductCategoryGroupTreeNode boss = new CProductCategoryGroupTreeNode(employee);		      
		      if (employee.isBoss) {                
		        for (Employee employee1 : employee.employees) {
		        	System.out.println(employee1.name + " -- is employee1");
		          CProductCategoryTreeNode emp = new CProductCategoryTreeNode(employee1);
		          boss.add(emp);
		        }
		      }
		      /* Nghia fixed */
		      root.add(boss);
		    }

		    JTree tree = new JTree(model);
		    tree.setRootVisible(true);
		    tree.setCellRenderer(new CTreeCellRenderer());            
		    pnlMain.add(tree, BorderLayout.CENTER);
		  }  
		  /** Returns an ImageIcon, or null if the path was invalid. */
		    protected static ImageIcon createImageIcon(String path) {
		        java.net.URL imgURL = TreeSample.class.getResource(path);
		        if (imgURL != null) {
		            return new ImageIcon(imgURL);
		        } else {
		            System.err.println("Couldn't find file: " + path);
		            return null;
		        }
		    }

		}