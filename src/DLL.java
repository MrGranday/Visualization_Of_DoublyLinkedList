import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DLL
{
    private DLL prev;
    private int data;
    private DLL next;

    DLL()
    {
        prev = next = null;
	data = 0;
    }

    DLL(int info)
    {
	data = info;
	prev = next = null;
    }

    public int getData()
    {
        return data;
    }

    public DLL getNext()
    {
	return next;
    }

    public DLL getPrevious()
    {
	return prev;
    }

	public void setNext(DLL nxt){
		next = nxt;
	}

	public void setPrevious(DLL prv){
		prev = prv;
	}
}

class ListPanel extends JPanel{
	private DLL lst;
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		DLL p;
		p = lst;
		int x=50,y=100;
		while(p!=null){
			g.drawRect(x-20,y-10,10,30);
			g.drawRect(x-10,y-10,30,30);
			g.drawRect(x+20,y-10,10,30);
			if(p.getNext()!=null){
				g.drawLine(x+25,y-5,x+80,y-5);
				g.drawLine(x+85,y+10,x+30,y+10);
				g.drawString(">",x+72,y);
				g.drawString("<",x+30,y+15);
			}
			g.drawString(Integer.toString(p.getData()),x-5,y+5);
			p = p.getNext();
			x+=100;
		}
	}

	public void setDLL(DLL dll){
		lst = dll;
	}
}

class DoublyLinkedList extends JFrame{
	private ListPanel panCenter;
	private JPanel panSouth;
	private JButton btnAddFirst,btnAddLast,btnAddPosition,
		btnRemoveFirst,btnRemoveLast,btnRemovePosition,btnRemoveVal,
		btnExit;
	private DLL first,last;
	private int count;


	public DoublyLinkedList(){
		panCenter = new ListPanel();			
		panSouth = new JPanel();

		btnAddFirst = new JButton("Add First");
		btnAddLast = new JButton("Add Last");
		btnAddPosition = new JButton("Add Position");
		btnRemoveFirst = new JButton("Remove First");
		btnRemoveLast = new JButton("Remove Last");
		btnRemovePosition  = new JButton("Remove By Position");
		btnRemoveVal = new JButton("Remove By Value");
		btnExit = new JButton("Exit");

		panSouth.setLayout(new FlowLayout());
		panSouth.add(btnAddFirst);
		panSouth.add(btnAddLast);
		panSouth.add(btnAddPosition);
		panSouth.add(btnRemoveFirst);
		panSouth.add(btnRemoveLast);
		panSouth.add(btnRemovePosition);
		panSouth.add(btnRemoveVal);
		panSouth.add(btnExit);

		setTitle("Doubly Linked List");
		setSize(900,300);
		
		add(panCenter,"Center");
		add(panSouth,"South");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		
		btnAddLast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				int val = Integer.parseInt(JOptionPane.showInputDialog("Enter value to be inserted:"));

				DLL p = new DLL(val);

				if(first == null)
					first = last = p;
				else{
					p.setNext(first);
					last.setPrevious(p);
					first = p;
				}
				count++;
				panCenter.setDLL(first);
				panCenter.repaint();
			}
		});

		btnAddFirst.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				int val = Integer.parseInt(JOptionPane.showInputDialog("Enter value to be inserted:"));
				DLL p = new DLL(val);
				
				if(first==null)
					first = last = p;
				else{
					last.setNext(p);	
					p.setPrevious(last);
					last = p;
				}
				count++;
				panCenter.setDLL(first);
				panCenter.repaint();
			}
		});
				
		btnAddPosition.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				int val = Integer.parseInt(JOptionPane.showInputDialog("Enter value to be inserted:"));
				int pos = Integer.parseInt(JOptionPane.showInputDialog("Enter position:"));
				if(pos<=0 || pos>count){
					JOptionPane.showMessageDialog(null,"Invalid position:"+pos);
					return;
				}
				int i=1;
				DLL p = new DLL(val);

				DLL q;

				q = first;
				while(i<pos && q!=null){
					i++;
					q = q.getNext();
				}

				if(pos==1){
					p.setNext(first);
					first.setPrevious(p);
					first = p;
				}
				else{
					q.getPrevious().setNext(p);
					p.setPrevious(q.getPrevious());
					p.setNext(q);
					q.setPrevious(p);
				}

				count++;
				panCenter.setDLL(first);
				panCenter.repaint();
			}
		});

		btnRemoveFirst.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(count==0){
					JOptionPane.showMessageDialog(null,"Linked list empty.");
					return;
				}
				if(first==last)
					first = last = null;
				else{
					first = first.getNext();
					first.setPrevious(null);
				}

				count--;
				panCenter.setDLL(first);
				panCenter.repaint();
			}
		});
					
		btnRemoveLast.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(count==0){
					JOptionPane.showMessageDialog(null,"Linked list empty.");
					return;
				}
				if(first==last)
					first = last = null;
				else{
					last = last.getPrevious();
					last.setNext(null);
				}
				count--;
				panCenter.setDLL(first);
				panCenter.repaint();
			}
		});
		
		btnRemovePosition.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(count==0){
					JOptionPane.showMessageDialog(null,"Linked list empty.");
					return;
				}
				int pos = Integer.parseInt(JOptionPane.showInputDialog("Enter position of the element to be deleted:"));

				if(pos<=0 || pos>count){
					JOptionPane.showMessageDialog(null,"Invalid position:"+pos);
					return;
				}

				int i=1;
				DLL p;

				p = first;
				while(p!=null && i<pos){
					i++;
					p=p.getNext();
				}

				if(first==last)
					first = last = null;
				else if(pos==1){
					first = first.getNext();
					first.setPrevious(null);
				}
				else if(pos==count){
					last = last.getPrevious();
					last.setNext(null);
				}
				else{
					p.getPrevious().setNext(p.getNext());
					p.getNext().setPrevious(p.getPrevious());
				}
				count--;
				panCenter.setDLL(first);
				panCenter.repaint();
			}
		});

		btnRemoveVal.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				if(count==0){
					JOptionPane.showMessageDialog(null,"Linked list empty.");
					return;
				}
				int val = Integer.parseInt(JOptionPane.showInputDialog("Enter value of the element to be deleted:"));

				DLL p;

				p = first;
				while(p!=null && p.getData()!=val)
					p = p.getNext();

				if(p==null){
					JOptionPane.showMessageDialog(null,val+" not found.");
					return;
				}
				if(first==last)
					first = last = null;
				else if(first==p){
					first = first.getNext();
					first.setPrevious(null);
				}
				else if(p==last){
					last = last.getPrevious();
					last.setNext(null);
				}
				else{
					p.getPrevious().setNext(p.getNext());
					p.getNext().setPrevious(p.getPrevious());
				}
				count--;
				panCenter.setDLL(first);
				panCenter.repaint();
			}
		});
				
		btnExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				dispose();
			}
		});
	}

    public static void main(String args[])
    {
        new DoublyLinkedList();
    }
}   
