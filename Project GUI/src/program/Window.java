package program;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.FlowLayout;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import program.DatabaseRetrieval.TableClass;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JComboBox;
import java.awt.Dimension;
import java.awt.Cursor;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import java.awt.SystemColor;

public class Window {

	private JFrame frame;
	private JTable table;
	private String[] highlightedRow;
	private TableClass displayedTable;
	private JTextField clothesIdTxt;
	private JTextField ColorTxt;
	private JTextField BrandTxt;
	private JTextField MaterialTxt;
	private JTextField PriceTxt;
	private JTextField GenderTxt;
	private JTextField dressLengthTxt;
	private JTextField dressTypeTxt;
	private JTextField hatSizeTxt;
	private JTextField hatTypeTxt;
	private JTextField pantsLengthTxt;
	private JTextField pantsFitTxt;
	private JTextField pantsWaistSizeTxt;
	private JTextField pantssTypeTxt;
	private JTextField pantsInseamTxt;
	private JTextField shoesSizeTxt;
	private JTextField shoesTypeTxt;
	private JTextField shoesLacesTxt;
	private JTextField topSleevesTxt;
	private JTextField topCollarTxt;
	private JTextField topSizeTxt;
	private JTextField topTypeTxt;
	private JTextField ClothesIDTxt;
	private JTextField ReceiptIDTxt;
	private JTextField ClientIDTxt;
	private JTextField DateTimeTxt;
	private JTextField MethodOfPayTxt;
	private JTextField WorkerIDTxt;

	// Create the application.
	public static void createWindow(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// Constructor
	private Window() {
		initialize();
	}

	public TableClass getDisplayedTable() {
		return displayedTable;
	}

	public void setDisplayedTable(TableClass displayedTable) {
		this.displayedTable = displayedTable;
	}

	public void setTableClick() {
		// table row select listener
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String selectedData = "";
				int selectedRow = table.getSelectedRows()[0];
				for (int col = 0; col < table.getColumnCount(); col++) {
					selectedData += (String) table.getValueAt(selectedRow, col) + ",";
				}
				selectedData = selectedData.substring(0, selectedData.length() - 1);
				highlightedRow = selectedData.split(",");
			}
		});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1031, 656);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

/////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////// Top Toolbar
/////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
		JPanel wrapper = new JPanel();
		frame.getContentPane().add(wrapper, BorderLayout.CENTER);
		wrapper.setLayout(new BorderLayout(0, 0));

		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		wrapper.add(toolBar, BorderLayout.NORTH);

// Delete Row Button	--------------------------------------------------------------------
		JButton btnDeleteRow = new JButton("Delete Row");
		btnDeleteRow.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (highlightedRow == null) {
					// No row selected give warning
					JOptionPane.showMessageDialog(null, "Select a row before trying to delete");
				} else {
					// Confirm Dialog
					int dialogButton = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to delete the selected row?", "Delete?",
							JOptionPane.YES_NO_CANCEL_OPTION);
					if (dialogButton == JOptionPane.YES_OPTION) {
						String deleteQuery = "DELETE FROM clothesshop." + (String) getDisplayedTable().tableName
								+ " WHERE ";
						// Add where clauses
						for (int i = 0; i < highlightedRow.length; i++) {
							if (highlightedRow[i].equals("null")) {
								continue;
							}
							deleteQuery += "`" + (String) displayedTable.columnNames[i] + "`='" + highlightedRow[i]
									+ "' AND ";
						}
						// Remove trailing AND
						deleteQuery = deleteQuery.substring(0, deleteQuery.length() - 5);
						System.out.println(deleteQuery);
						// Execute query
						try {
							DatabaseRetrieval.executeUpdate(deleteQuery);
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
					JOptionPane.showMessageDialog(null, "Row Deleted", "Success", JOptionPane.OK_OPTION);
				}

			}
		});
		toolBar.add(btnDeleteRow);

		JPanel panel_5 = new JPanel();
		toolBar.add(panel_5);

		ClothesIDTxt = new JTextField();
		ClothesIDTxt.setText("ClothesID");
		panel_5.add(ClothesIDTxt);
		ClothesIDTxt.setColumns(7);

		ReceiptIDTxt = new JTextField();
		ReceiptIDTxt.setText("ReceiptID");
		panel_5.add(ReceiptIDTxt);
		ReceiptIDTxt.setColumns(7);

		ClientIDTxt = new JTextField();
		ClientIDTxt.setText("ClientID");
		panel_5.add(ClientIDTxt);
		ClientIDTxt.setColumns(7);

		DateTimeTxt = new JTextField();
		DateTimeTxt.setText("YYYY-MM-DD");
		panel_5.add(DateTimeTxt);
		DateTimeTxt.setColumns(12);

		MethodOfPayTxt = new JTextField();
		MethodOfPayTxt.setText("Method of Payment");
		panel_5.add(MethodOfPayTxt);
		MethodOfPayTxt.setColumns(13);

		WorkerIDTxt = new JTextField();
		WorkerIDTxt.setText("WorkerID");
		panel_5.add(WorkerIDTxt);
		WorkerIDTxt.setColumns(7);

		JButton btnPurchase = new JButton("Purchase");
		btnPurchase.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if (ClothesIDTxt.getText().equals("ClothesID") || ReceiptIDTxt.getText().equals("ReceiptID")
							|| ClientIDTxt.getText().equals("ClientID") || DateTimeTxt.getText().equals("YYYY-MM-DD")
							|| MethodOfPayTxt.getText().equals("Method of Payment")
							|| WorkerIDTxt.getText().equals("WorkerID")) {
						throw new Exception("Fill out the necessary fields.");
					} else {
						// Check if clothes are bought
						if (DatabaseRetrieval.executeQuery("SELECT ClientID FROM clothesshop.Clothes") != null) {
							// Update clothes' clientID workerID
							DatabaseRetrieval.executeUpdate("UPDATE clothesshop.Clothes SET ClientID="
									+ ClientIDTxt.getText() + ", WorkerID=" + WorkerIDTxt.getText());
						} else {
							throw new Exception("Clothes is already bought.");
						}

						// Add Receipt
						DatabaseRetrieval.executeUpdate(
								"INSERT INTO clothesshop.Receipt (`ClientID`, `ReceiptID`, `Date_Time`, `MethodOfPayment`,`WorkerID`) VALUES "
										+ "('" + ClientIDTxt.getText() + "', '" + ReceiptIDTxt.getText() + "', '"
										+ DateTimeTxt.getText() + "', '" + MethodOfPayTxt.getText() + "', '"
										+ WorkerIDTxt.getText() + "')");

						// Add clothesReceipt
						DatabaseRetrieval.executeUpdate(
								"INSERT INTO clothesshop.ClothesReceipt (`ClothesID`, `ReceiptID`) VALUES " + "('"
										+ ClothesIDTxt.getText() + "', '" + ReceiptIDTxt.getText() + "')");

						JOptionPane.showMessageDialog(null, "Your purchase was successful!");
					}

				} catch (ClassNotFoundException | SQLException e1) {
					JOptionPane.showMessageDialog(null, "Receipt already exists");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}

				ClothesIDTxt.setText("ClothesID");
				ReceiptIDTxt.setText("ReceiptID");
				ClientIDTxt.setText("ClientID");
				DateTimeTxt.setText("YYYY-MM-DD");
				MethodOfPayTxt.setText("Method of Payment");
				WorkerIDTxt.setText("WorkerID");

			}
		});
		panel_5.add(btnPurchase);

/////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////// Table Wrapper
/////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
		JPanel main = new JPanel();
		main.setBackground(Color.WHITE);
		wrapper.add(main, BorderLayout.CENTER);
		main.setLayout(new BorderLayout(0, 0));

		JPanel tableWrapper = new JPanel();
		tableWrapper.setBackground(Color.WHITE);
		main.add(tableWrapper, BorderLayout.CENTER);
		tableWrapper.setLayout(new BorderLayout(0, 0));

		Component nSpace = Box.createVerticalStrut(20);
		main.add(nSpace, BorderLayout.NORTH);

		Component sSpace = Box.createVerticalStrut(15);
		main.add(sSpace, BorderLayout.SOUTH);

		Component wSpace = Box.createHorizontalStrut(20);
		main.add(wSpace, BorderLayout.WEST);

		Component eSpace = Box.createHorizontalStrut(20);
		main.add(eSpace, BorderLayout.EAST);

/////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////// Bottom Toolbar
/////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////
		JPanel bottomTools = new JPanel();
		bottomTools.setMaximumSize(new Dimension(100000, 32767));
		bottomTools.setPreferredSize(new Dimension(10, 275));
		wrapper.add(bottomTools, BorderLayout.SOUTH);

// Show All Clothes Button	--------------------------------------------------------------------
		JButton btnShowAllClothes = new JButton("Show All Clothes");
		btnShowAllClothes.setPreferredSize(new Dimension(300, 23));
		btnShowAllClothes.setBounds(40, 11, 133, 23);
		btnShowAllClothes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableClass c = DatabaseRetrieval.display("SELECT * FROM AllClothes");

				if (table == null) {

					table = new JTable(c.data, c.columnNames);
					table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
					table.setBackground(Color.WHITE);

					tableWrapper.setLayout(new BorderLayout());
					tableWrapper.add(table.getTableHeader(), BorderLayout.PAGE_START);
					tableWrapper.add(table, BorderLayout.CENTER);
					frame.validate();
					setTableClick();
				} else {
					table.clearSelection();
					highlightedRow = null;
					DefaultTableModel data = new DefaultTableModel(c.data, c.columnNames);
					table.setModel(data);
				}

				// Renaming the table header
				JTableHeader th = table.getTableHeader();
				TableColumnModel tcm = th.getColumnModel();
				TableColumn tc = tcm.getColumn(2);
				tc.setHeaderValue("Color");
				tc = tcm.getColumn(3);
				tc.setHeaderValue("Brand");
				tc = tcm.getColumn(4);
				tc.setHeaderValue("Material");
				tc = tcm.getColumn(5);
				tc.setHeaderValue("Price");
				tc = tcm.getColumn(6);
				tc.setHeaderValue("Gender");
				tc = tcm.getColumn(7);
				tc.setHeaderValue("Dress Length");
				tc = tcm.getColumn(8);
				tc.setHeaderValue("Dress Type");
				tc = tcm.getColumn(9);
				tc.setHeaderValue("Hat Size");
				tc = tcm.getColumn(10);
				tc.setHeaderValue("Hat Type");
				tc = tcm.getColumn(11);
				tc.setHeaderValue("Pants Fit");
				tc = tcm.getColumn(12);
				tc.setHeaderValue("Pants Inseam");
				tc = tcm.getColumn(13);
				tc.setHeaderValue("Pants Length");
				tc = tcm.getColumn(14);
				tc.setHeaderValue("Pants Type");
				tc = tcm.getColumn(15);
				tc.setHeaderValue("Pants Waist Size");
				tc = tcm.getColumn(16);
				tc.setHeaderValue("Shoe Laces");
				tc = tcm.getColumn(18);
				tc.setHeaderValue("Shoe Type");
				tc = tcm.getColumn(19);
				tc.setHeaderValue("Top Collar");
				tc = tcm.getColumn(20);
				tc.setHeaderValue("Top Size");
				tc = tcm.getColumn(21);
				tc.setHeaderValue("Top Sleeves");
				tc = tcm.getColumn(22);
				tc.setHeaderValue("Top Type");

				th.repaint();
				setDisplayedTable(c);
			}
		});
		bottomTools.setLayout(null);
		bottomTools.add(btnShowAllClothes);

// Show Suppliers Button	--------------------------------------------------------------------
		JButton btnShowSuppliers = new JButton("Show Suppliers");
		btnShowSuppliers.setPreferredSize(new Dimension(500, 23));
		btnShowSuppliers.setBounds(181, 11, 123, 23);
		btnShowSuppliers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnShowSuppliers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TableClass c = DatabaseRetrieval.display("select * from Suppliers");

				if (table == null) {
					table = new JTable(c.data, c.columnNames);
					table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
					table.setBackground(Color.WHITE);

					tableWrapper.setLayout(new BorderLayout());
					tableWrapper.add(table.getTableHeader(), BorderLayout.PAGE_START);
					tableWrapper.add(table, BorderLayout.CENTER);
					frame.validate();
					setTableClick();
				} else {
					table.clearSelection();
					highlightedRow = null;
					DefaultTableModel data = new DefaultTableModel(c.data, c.columnNames);
					table.setModel(data);
				}
				setDisplayedTable(c);
			}

		});

// Show Clients Button	--------------------------------------------------------------------
		JButton btnShowClients = new JButton("Show Clients");
		btnShowClients.setBounds(40, 52, 133, 23);
		btnShowClients.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnShowClients.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TableClass c = DatabaseRetrieval.display("select * from Clients");
				if (table == null) {

					table = new JTable(c.data, c.columnNames);
					table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
					table.setBackground(Color.WHITE);

					tableWrapper.setLayout(new BorderLayout());
					tableWrapper.add(table.getTableHeader(), BorderLayout.PAGE_START);
					tableWrapper.add(table, BorderLayout.CENTER);
					frame.validate();
					setTableClick();
				} else {
					table.clearSelection();
					highlightedRow = null;
					DefaultTableModel data = new DefaultTableModel(c.data, c.columnNames);
					table.setModel(data);
				}
				setDisplayedTable(c);
			}
		});
		bottomTools.add(btnShowClients);

		JPanel panel = new JPanel();
		panel.setBounds(314, 11, 661, 37);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		bottomTools.add(panel);

// Show All Employees Button	--------------------------------------------------------------------
		JButton btnNewButton_3 = new JButton("All Employees");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableClass c = DatabaseRetrieval.display("SELECT * FROM Employees");
				if (table == null) {

					table = new JTable(c.data, c.columnNames);
					table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
					table.setBackground(Color.WHITE);

					tableWrapper.setLayout(new BorderLayout());
					tableWrapper.add(table.getTableHeader(), BorderLayout.PAGE_START);
					tableWrapper.add(table, BorderLayout.CENTER);
					frame.validate();
					setTableClick();
				} else {
					table.clearSelection();
					highlightedRow = null;
					DefaultTableModel data = new DefaultTableModel(c.data, c.columnNames);
					table.setModel(data);
				}
				setDisplayedTable(c);
			}
		});
		btnNewButton_3.setBounds(181, 52, 123, 23);
		bottomTools.add(btnNewButton_3);
		bottomTools.add(btnShowSuppliers);

/////////////////////////////////////////
/////////////////////////////////////////
//	PANEL FOR CLOTHES FROM SUPPLIER
/////////////////////////////////////////
/////////////////////////////////////////
// Fetch Categories/Colors/Materials/Brands data from database
		// Retrieve ResultSets from db
		ResultSet categRes = DatabaseRetrieval.executeQuery("SELECT Category FROM Clothes");
		ResultSet colorRes = DatabaseRetrieval.executeQuery("SELECT Color FROM Clothes");
		ResultSet matRes = DatabaseRetrieval.executeQuery("SELECT Material FROM Clothes");
		ResultSet brandRes = DatabaseRetrieval.executeQuery("SELECT Brand FROM Clothes");
		// Initialize result arrays
		ArrayList<String> categoryResArray = new ArrayList<String>();
		ArrayList<String> colorResArray = new ArrayList<String>();
		ArrayList<String> matResArray = new ArrayList<String>();
		ArrayList<String> brandResArray = new ArrayList<String>();
		// Add data from resultset to corresponding array
		try {
			while (categRes.next()) {
				categoryResArray.add(categRes.getString("Category"));
			}
			while (colorRes.next()) {
				colorResArray.add(colorRes.getString("Color"));
			}
			while (matRes.next()) {
				matResArray.add(matRes.getString("Material"));
			}
			while (brandRes.next()) {
				brandResArray.add(brandRes.getString("Brand"));
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		// Transform result arrays into string arrays since the comboBoxModel only
		// accepts it
		String[] CategoriesList = new String[categoryResArray.size() + 1];
		String[] ColorsList = new String[colorResArray.size() + 1];
		String[] MaterialsList = new String[matResArray.size() + 1];
		String[] BrandsList = new String[brandResArray.size() + 1];
		// Set Model to the resulting string arrays
		CategoriesList = categoryResArray.toArray(CategoriesList);
		ColorsList = colorResArray.toArray(ColorsList);
		MaterialsList = matResArray.toArray(MaterialsList);
		BrandsList = brandResArray.toArray(BrandsList);

// Select Categories DropDown
		JComboBox Categories = new JComboBox(CategoriesList);
		Categories.setToolTipText("Category");
		Categories.setPreferredSize(new Dimension(120, 22));
		panel.add(Categories);
		Categories.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Categories.insertItemAt("All Categories", 0);
		Categories.setSelectedIndex(0);

// Select Colors DropDown
		JComboBox Color = new JComboBox(ColorsList);
		Color.setToolTipText("Color");
		Color.setPreferredSize(new Dimension(100, 22));
		panel.add(Color);
		Color.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Color.insertItemAt("All Colors", 0);
		Color.setSelectedIndex(0);

// Select Materials DropDown
		JComboBox Material = new JComboBox(MaterialsList);
		Material.setToolTipText("Material");
		Material.setPreferredSize(new Dimension(100, 22));
		panel.add(Material);
		Material.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Material.insertItemAt("All Materials", 0);
		Material.setSelectedIndex(0);

// Select Brands DropDown
		JComboBox Brand = new JComboBox(BrandsList);
		Brand.setToolTipText("Brand");
		Brand.setPreferredSize(new Dimension(100, 22));
		panel.add(Brand);
		Brand.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		Brand.insertItemAt("All Brands", 0);
		Brand.setSelectedIndex(0);

// Show Clothes based on Categories/Colors/Materials/Brands Button	--------------------------------------------------------------------
		JButton btnShowClothes = new JButton("Show Clothes");
		btnShowClothes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "SELECT * FROM AllClothes";
				if (Categories.getSelectedItem() != "All Categories") {
					sql += " WHERE AllClothes.Category = '" + Categories.getSelectedItem() + "'";
				}
				if (Color.getSelectedItem() != "All Colors") {
					if (sql.contains("WHERE")) {
						sql += " AND AllClothes.Color = '" + Color.getSelectedItem() + "'";
					} else {
						sql += " WHERE AllClothes.Color = '" + Color.getSelectedItem() + "'";
					}
				}
				if (Material.getSelectedItem() != "All Materials") {
					if (sql.contains("WHERE")) {
						sql += " AND AllClothes.Material = '" + Material.getSelectedItem() + "'";
					} else {
						sql += " WHERE AllClothes.Material = '" + Material.getSelectedItem() + "'";
					}
				}
				if (Brand.getSelectedItem() != "All Brands") {
					if (sql.contains("WHERE")) {
						sql += " AND AllClothes.Brand = '" + Brand.getSelectedItem() + "'";
					} else {
						sql += " WHERE AllClothes.Brand = '" + Brand.getSelectedItem() + "'";
					}
				}
				TableClass c = DatabaseRetrieval.display(sql);
				if (table == null) {
					table = new JTable(c.data, c.columnNames);
					table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

					tableWrapper.setLayout(new BorderLayout());
					tableWrapper.add(table.getTableHeader(), BorderLayout.PAGE_START);
					tableWrapper.add(table, BorderLayout.CENTER);
					frame.validate();
					setTableClick();
				} else {
					table.clearSelection();
					highlightedRow = null;
					DefaultTableModel data = new DefaultTableModel(c.data, c.columnNames);
					table.setModel(data);
				}
				// Renaming the table header
				JTableHeader th = table.getTableHeader();
				TableColumnModel tcm = th.getColumnModel();
				TableColumn tc = tcm.getColumn(2);
				tc.setHeaderValue("Color");
				tc = tcm.getColumn(3);
				tc.setHeaderValue("Brand");
				tc = tcm.getColumn(4);
				tc.setHeaderValue("Material");
				tc = tcm.getColumn(5);
				tc.setHeaderValue("Price");
				tc = tcm.getColumn(6);
				tc.setHeaderValue("Gender");
				tc = tcm.getColumn(7);
				tc.setHeaderValue("Dress Length");
				tc = tcm.getColumn(8);
				tc.setHeaderValue("Dress Type");
				tc = tcm.getColumn(9);
				tc.setHeaderValue("Hat Size");
				tc = tcm.getColumn(10);
				tc.setHeaderValue("Hat Type");
				tc = tcm.getColumn(11);
				tc.setHeaderValue("Pants Fit");
				tc = tcm.getColumn(12);
				tc.setHeaderValue("Pants Inseam");
				tc = tcm.getColumn(13);
				tc.setHeaderValue("Pants Length");
				tc = tcm.getColumn(14);
				tc.setHeaderValue("Pants Type");
				tc = tcm.getColumn(15);
				tc.setHeaderValue("Pants Waist Size");
				tc = tcm.getColumn(16);
				tc.setHeaderValue("Shoe Laces");
				tc = tcm.getColumn(18);
				tc.setHeaderValue("Shoe Type");
				tc = tcm.getColumn(19);
				tc.setHeaderValue("Top Collar");
				tc = tcm.getColumn(20);
				tc.setHeaderValue("Top Size");
				tc = tcm.getColumn(21);
				tc.setHeaderValue("Top Sleeves");
				tc = tcm.getColumn(22);
				tc.setHeaderValue("Top Type");
				th.repaint();
				setDisplayedTable(c);
			}
		});

		panel.add(btnShowClothes);
		btnShowClothes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnShowClothes.setBackground(new Color(240, 240, 240));

/////////////////////////////////////////
/////////////////////////////////////////
//	PANEL FOR CLOTHES FROM SUPPLIER
/////////////////////////////////////////
/////////////////////////////////////////
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(489, 57, 324, 37);
		bottomTools.add(panel_1);

		// Fetch suppliers from database
		ResultSet supRes = DatabaseRetrieval.executeQuery("SELECT * FROM Suppliers");
		// Initialize result arrays
		ArrayList<String> suppliersResArray = new ArrayList<String>();
		// Add data from resultset to corresponding array
		try {
			while (supRes.next()) {
				suppliersResArray.add(supRes.getString("Name"));
			}

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		// Transform result arrays into string arrays since the comboBoxModel only
		// accepts it
		String[] SuppliersList = new String[suppliersResArray.size() + 1];
		// Set Model to the resulting string arrays
		SuppliersList = suppliersResArray.toArray(SuppliersList);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JComboBox<?> Suppliers = new JComboBox<Object>(SuppliersList);
		Suppliers.setToolTipText("Suppliers");
		Suppliers.setPreferredSize(new Dimension(100, 22));
		panel_1.add(Suppliers);
		Suppliers.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JButton btnClothesFromSupplier = new JButton("Clothes From Supplier");
		btnClothesFromSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableClass c = DatabaseRetrieval
						.display("SELECT * FROM `SuppliersClothes` WHERE Name = '" + Suppliers.getSelectedItem() + "'");

				if (table == null) {

					table = new JTable(c.data, c.columnNames);
					table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

					tableWrapper.setLayout(new BorderLayout());
					tableWrapper.add(table.getTableHeader(), BorderLayout.PAGE_START);
					tableWrapper.add(table, BorderLayout.CENTER);
					frame.validate();
					setTableClick();
				} else {
					table.clearSelection();
					highlightedRow = null;
					DefaultTableModel data = new DefaultTableModel(c.data, c.columnNames);
					table.setModel(data);
				}
			}
		});
		panel_1.add(btnClothesFromSupplier);

		JButton btnClothesBought = new JButton("Show All Clothes Bought");
		btnClothesBought.setPreferredSize(new Dimension(200, 23));
		btnClothesBought.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableClass c = DatabaseRetrieval.display("SELECT * FROM AllClientsReceipts");

				if (table == null) {

					table = new JTable(c.data, c.columnNames);
					table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

					tableWrapper.setLayout(new BorderLayout());
					tableWrapper.add(table.getTableHeader(), BorderLayout.PAGE_START);
					tableWrapper.add(table, BorderLayout.CENTER);
					frame.validate();
					setTableClick();
				} else {
					table.clearSelection();
					highlightedRow = null;
					DefaultTableModel data = new DefaultTableModel(c.data, c.columnNames);
					table.setModel(data);
				}
			}
		});
		btnClothesBought.setBounds(40, 101, 264, 23);
		bottomTools.add(btnClothesBought);

/////////////////////////////////////////
/////////////////////////////////////////
//PANEL FOR BOUGHT CLOTHES BY RECEIPT ID
/////////////////////////////////////////
/////////////////////////////////////////
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(314, 100, 324, 37);
		bottomTools.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JComboBox<?> ReceiptIDs = new JComboBox<Object>();
		ReceiptIDs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Fetch receipt from database
				ResultSet recRes = DatabaseRetrieval.executeQuery("SELECT * FROM Receipt");
				// Initialize result arrays
				ArrayList<String> receiptResArray = new ArrayList<String>();
				// Add data from resultset to corresponding array
				try {
					while (recRes.next()) {
						receiptResArray.add(recRes.getString("ReceiptID"));
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				// Transform result arrays into string arrays since the comboBoxModel only
				// accepts it
				String[] ReceiptList = new String[receiptResArray.size()];
				for (int i = 0; i < receiptResArray.size(); i++) {
					ReceiptList[i] = receiptResArray.get(i);
				}
				// Set Model to the resulting string arrays
				DefaultComboBoxModel model = new DefaultComboBoxModel(ReceiptList);
				ReceiptIDs.setModel(model);
			}
		});
		ReceiptIDs.setToolTipText("Receipt ID");
		ReceiptIDs.setPreferredSize(new Dimension(100, 22));
		panel_2.add(ReceiptIDs);
		ReceiptIDs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JButton btnBoughtClothesByReceiptID = new JButton("Bought Clothes by Receipt ID");
		btnBoughtClothesByReceiptID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableClass c = DatabaseRetrieval.display(
						"SELECT * FROM AllClientsReceipts WHERE ReceiptID = '" + ReceiptIDs.getSelectedItem() + "'");

				if (table == null) {

					table = new JTable(c.data, c.columnNames);
					table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

					tableWrapper.setLayout(new BorderLayout());
					tableWrapper.add(table.getTableHeader(), BorderLayout.PAGE_START);
					tableWrapper.add(table, BorderLayout.CENTER);
					frame.validate();
					setTableClick();
				} else {
					table.clearSelection();
					highlightedRow = null;
					DefaultTableModel data = new DefaultTableModel(c.data, c.columnNames);
					table.setModel(data);
				}
			}
		});
		panel_2.add(btnBoughtClothesByReceiptID);

/////////////////////////////////////////
/////////////////////////////////////////
//PANEL FOR BOUGHT CLOTHES BY CLIENT ID
/////////////////////////////////////////
/////////////////////////////////////////
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_3.setBounds(651, 100, 324, 37);
		bottomTools.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JComboBox<?> ClientIDs = new JComboBox<Object>();
		ClientIDs.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Fetch receipt from database
				ResultSet clientRes = DatabaseRetrieval.executeQuery("SELECT * FROM Clients");
				// Initialize result arrays
				ArrayList<String> clientsResArray = new ArrayList<String>();
				// Add data from resultset to corresponding array
				try {
					while (clientRes.next()) {
						clientsResArray.add(clientRes.getString("ClientID"));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				// Transform result arrays into string arrays since the comboBoxModel only
				// accepts it
				String[] ClientList = new String[clientsResArray.size()];
				for (int i = 0; i < clientsResArray.size(); i++) {
					ClientList[i] = clientsResArray.get(i);
				}
				// Set Model to the resulting string arrays
				DefaultComboBoxModel model = new DefaultComboBoxModel(ClientList);
				ClientIDs.setModel(model);
			}
		});
		ClientIDs.setToolTipText("Client ID");
		ClientIDs.setPreferredSize(new Dimension(100, 22));
		panel_3.add(ClientIDs);
		ClientIDs.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JButton btnBoughtClothesByClientID = new JButton("Bought Clothes by Client ID");
		btnBoughtClothesByClientID.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableClass c = DatabaseRetrieval.display(
						"SELECT * FROM AllClientsReceipts WHERE ClientID = '" + ClientIDs.getSelectedItem() + "'");

				if (table == null) {

					table = new JTable(c.data, c.columnNames);
					table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

					tableWrapper.setLayout(new BorderLayout());
					tableWrapper.add(table.getTableHeader(), BorderLayout.PAGE_START);
					tableWrapper.add(table, BorderLayout.CENTER);
					frame.validate();
					setTableClick();
				} else {
					table.clearSelection();
					highlightedRow = null;
					DefaultTableModel data = new DefaultTableModel(c.data, c.columnNames);
					table.setModel(data);
				}
			}
		});
		panel_3.add(btnBoughtClothesByClientID);

//	Show All Employees and Their Managers Button --------------------------------------------------------------------
		JButton btnEmployeesManagers = new JButton("Show All Employees and Their Managers");
		btnEmployeesManagers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableClass c = DatabaseRetrieval.display(
						"SELECT Employee.WorkerID, Employee.`Employee Name`, ManagerManages.workerid, `Manager Name`\r\n"
								+ "FROM (SELECT Worker.Name AS `Employee Name` , Employees.WorkerID\r\n"
								+ "    FROM Worker, Employees\r\n"
								+ "    WHERE Worker.WorkerID=Employees.WorkerID) AS `Employee`\r\n"
								+ "LEFT JOIN (SELECT *\r\n" + "    FROM Manages\r\n"
								+ "    LEFT JOIN (SELECT Worker.Name AS `Manager Name`, Manager.WorkerID\r\n"
								+ "            FROM Worker, Manager\r\n"
								+ "            WHERE Worker.WorkerID=Manager.WorkerID) AS `Manager`\r\n"
								+ "    ON Manages.ManagerWorkerID=Manager.WorkerID) AS `ManagerManages`\r\n"
								+ "ON Employee.WorkerID=ManagerManages.EmployeeWorkerID");

				if (table == null) {

					table = new JTable(c.data, c.columnNames);
					table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

					tableWrapper.setLayout(new BorderLayout());
					tableWrapper.add(table.getTableHeader(), BorderLayout.PAGE_START);
					tableWrapper.add(table, BorderLayout.CENTER);
					frame.validate();
					setTableClick();
				} else {
					table.clearSelection();
					highlightedRow = null;
					DefaultTableModel data = new DefaultTableModel(c.data, c.columnNames);
					table.setModel(data);
				}

				// Renaming the table header
				JTableHeader th = table.getTableHeader();
				TableColumnModel tcm = th.getColumnModel();
				TableColumn tc = tcm.getColumn(0);
				tc.setHeaderValue("Employee ID");
				tc = tcm.getColumn(1);
				tc.setHeaderValue("Employee Name");
				tc = tcm.getColumn(2);
				tc.setHeaderValue("Manager ID");
				tc = tcm.getColumn(3);
				tc.setHeaderValue("Manager Name");
				th.repaint();
			}

		});
		btnEmployeesManagers.setBounds(40, 135, 264, 23);
		bottomTools.add(btnEmployeesManagers);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_4.setBounds(40, 169, 935, 96);
		bottomTools.add(panel_4);
		panel_4.setLayout(null);

		JComboBox Category = new JComboBox();
		Category.setBounds(22, 36, 136, 22);
		Category.addItem("Dresses");
		Category.addItem("Hats");
		Category.addItem("Pants");
		Category.addItem("Shoes");
		Category.addItem("Tops");
		panel_4.add(Category);

		clothesIdTxt = new JTextField();
		clothesIdTxt.setBackground(SystemColor.info);
		clothesIdTxt.setText("Clothes ID");
		clothesIdTxt.setToolTipText("12 (int)");
		clothesIdTxt.setBounds(360, 8, 86, 20);
		panel_4.add(clothesIdTxt);
		clothesIdTxt.setColumns(10);

		ColorTxt = new JTextField();
		ColorTxt.setBackground(SystemColor.info);
		ColorTxt.setText("Color");
		ColorTxt.setToolTipText("red (string)");
		ColorTxt.setBounds(456, 8, 86, 20);
		panel_4.add(ColorTxt);
		ColorTxt.setColumns(10);

		BrandTxt = new JTextField();
		BrandTxt.setBackground(SystemColor.info);
		BrandTxt.setText("Brand");
		BrandTxt.setBounds(547, 8, 86, 20);
		BrandTxt.setToolTipText("gucci (string)");
		panel_4.add(BrandTxt);
		BrandTxt.setColumns(10);

		MaterialTxt = new JTextField();
		MaterialTxt.setBackground(SystemColor.info);
		MaterialTxt.setText("Material");
		MaterialTxt.setToolTipText("silk (string)");
		MaterialTxt.setBounds(643, 8, 86, 20);
		panel_4.add(MaterialTxt);
		MaterialTxt.setColumns(10);

		PriceTxt = new JTextField();
		PriceTxt.setBackground(SystemColor.info);
		PriceTxt.setText("Price");
		PriceTxt.setToolTipText("23.10 (dec(4,2))");
		PriceTxt.setBounds(739, 8, 86, 20);
		panel_4.add(PriceTxt);
		PriceTxt.setColumns(10);

		GenderTxt = new JTextField();
		GenderTxt.setBackground(SystemColor.info);
		GenderTxt.setText("Gender");
		GenderTxt.setToolTipText("F/M (Char)");
		GenderTxt.setBounds(835, 8, 86, 20);
		panel_4.add(GenderTxt);
		GenderTxt.setColumns(10);

		dressLengthTxt = new JTextField();
		dressLengthTxt.setBackground(new Color(224, 255, 255));
		dressLengthTxt.setText("Dress Length");
		dressLengthTxt.setToolTipText("30in (string)");
		dressLengthTxt.setBounds(168, 37, 86, 20);
		panel_4.add(dressLengthTxt);
		dressLengthTxt.setColumns(10);

		dressTypeTxt = new JTextField();
		dressTypeTxt.setBackground(new Color(224, 255, 255));
		dressTypeTxt.setText("Dress Type");
		dressTypeTxt.setToolTipText("X (string)");
		dressTypeTxt.setBounds(264, 37, 86, 20);
		panel_4.add(dressTypeTxt);
		dressTypeTxt.setColumns(10);

		hatSizeTxt = new JTextField();
		hatSizeTxt.setBackground(new Color(230, 230, 250));
		hatSizeTxt.setText("Hat Size");
		hatSizeTxt.setToolTipText("small (string)");
		hatSizeTxt.setBounds(360, 37, 86, 20);
		panel_4.add(hatSizeTxt);
		hatSizeTxt.setColumns(10);

		hatTypeTxt = new JTextField();
		hatTypeTxt.setBackground(new Color(230, 230, 250));
		hatTypeTxt.setText("Hat Type");
		hatTypeTxt.setToolTipText("sun hat (string)");
		hatTypeTxt.setBounds(456, 37, 86, 20);
		panel_4.add(hatTypeTxt);
		hatTypeTxt.setColumns(10);

		pantsLengthTxt = new JTextField();
		pantsLengthTxt.setBackground(new Color(255, 240, 245));
		pantsLengthTxt.setToolTipText("30in (string)");
		pantsLengthTxt.setText("Pants Length");
		pantsLengthTxt.setBounds(547, 37, 86, 20);
		panel_4.add(pantsLengthTxt);
		pantsLengthTxt.setColumns(10);

		pantsFitTxt = new JTextField();
		pantsFitTxt.setBackground(new Color(255, 240, 245));
		pantsFitTxt.setToolTipText("X (string)");
		pantsFitTxt.setText("Pants Fit");
		pantsFitTxt.setBounds(643, 37, 86, 20);
		panel_4.add(pantsFitTxt);
		pantsFitTxt.setColumns(10);

		pantsWaistSizeTxt = new JTextField();
		pantsWaistSizeTxt.setBackground(new Color(255, 240, 245));
		pantsWaistSizeTxt.setToolTipText("X (string)");
		pantsWaistSizeTxt.setText("Waist Size");
		pantsWaistSizeTxt.setBounds(739, 37, 86, 20);
		panel_4.add(pantsWaistSizeTxt);
		pantsWaistSizeTxt.setColumns(10);

		pantssTypeTxt = new JTextField();
		pantssTypeTxt.setBackground(new Color(255, 240, 245));
		pantssTypeTxt.setText("Pants Type");
		pantssTypeTxt.setToolTipText("X (string)");
		pantssTypeTxt.setBounds(835, 37, 86, 20);
		panel_4.add(pantssTypeTxt);
		pantssTypeTxt.setColumns(10);

		pantsInseamTxt = new JTextField();
		pantsInseamTxt.setBackground(new Color(255, 240, 245));
		pantsInseamTxt.setText("Pants Inseam");
		pantsInseamTxt.setToolTipText("X (string)");
		pantsInseamTxt.setBounds(168, 66, 86, 20);
		panel_4.add(pantsInseamTxt);
		pantsInseamTxt.setColumns(10);

		shoesSizeTxt = new JTextField();
		shoesSizeTxt.setBackground(new Color(255, 182, 193));
		shoesSizeTxt.setText("Shoe Size");
		shoesSizeTxt.setToolTipText("X (string)");
		shoesSizeTxt.setBounds(264, 66, 86, 20);
		panel_4.add(shoesSizeTxt);
		shoesSizeTxt.setColumns(10);

		shoesTypeTxt = new JTextField();
		shoesTypeTxt.setBackground(new Color(255, 182, 193));
		shoesTypeTxt.setText("Shoe Type");
		shoesTypeTxt.setToolTipText("X (string)");
		shoesTypeTxt.setBounds(360, 66, 86, 20);
		panel_4.add(shoesTypeTxt);
		shoesTypeTxt.setColumns(10);

		shoesLacesTxt = new JTextField();
		shoesLacesTxt.setBackground(new Color(255, 182, 193));
		shoesLacesTxt.setText("Shoe Laces");
		shoesLacesTxt.setToolTipText("X (string)");
		shoesLacesTxt.setBounds(456, 66, 86, 20);
		panel_4.add(shoesLacesTxt);
		shoesLacesTxt.setColumns(10);

		topSleevesTxt = new JTextField();
		topSleevesTxt.setBackground(new Color(255, 218, 185));
		topSleevesTxt.setText("Top Sleeves");
		topSleevesTxt.setToolTipText("X (string)");
		topSleevesTxt.setBounds(547, 66, 86, 20);
		panel_4.add(topSleevesTxt);
		topSleevesTxt.setColumns(10);

		topCollarTxt = new JTextField();
		topCollarTxt.setBackground(new Color(255, 218, 185));
		topCollarTxt.setText("Top Collar");
		topCollarTxt.setToolTipText("X (string)");
		topCollarTxt.setBounds(643, 66, 86, 20);
		panel_4.add(topCollarTxt);
		topCollarTxt.setColumns(10);

		topSizeTxt = new JTextField();
		topSizeTxt.setBackground(new Color(255, 218, 185));
		topSizeTxt.setBounds(739, 66, 86, 20);
		panel_4.add(topSizeTxt);
		topSizeTxt.setText("Top Size");
		topSizeTxt.setToolTipText("X (string)");
		topSizeTxt.setColumns(10);

		topTypeTxt = new JTextField();
		topTypeTxt.setBackground(new Color(255, 218, 185));
		topTypeTxt.setText("Top Type");
		topTypeTxt.setToolTipText("X (string)");
		topTypeTxt.setBounds(835, 66, 86, 20);
		panel_4.add(topTypeTxt);
		topTypeTxt.setColumns(10);

		JButton btnInsert = new JButton("Insert");
		btnInsert.setBounds(22, 7, 278, 23);
		panel_4.add(btnInsert);
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ResultSet testRes = DatabaseRetrieval
							.executeQuery("SELECT ClothesID FROM clothesshop.AllClothes WHERE ClothesID='"
									+ clothesIdTxt.getText() + "';");
					ArrayList<String> testResArray = new ArrayList<String>();
					while (testRes.next()) {
						testResArray.add(testRes.getString("ClothesID"));
					}
					if (!testResArray.isEmpty()) {
						throw new Exception("Clothes Item Already Exists (Duplicate ClothesID)");
					}

					String clothesSql = "INSERT INTO clothesshop.Clothes (clothesid, color, brand, material, price, gender, category) VALUES ('"
							+ clothesIdTxt.getText() + "', '" + ColorTxt.getText() + "', '" + BrandTxt.getText()
							+ "', '" + MaterialTxt.getText() + "', '" + PriceTxt.getText() + "', '"
							+ GenderTxt.getText() + "', '" + Category.getSelectedItem() + "')";
					String sql = "INSERT INTO clothesshop." + Category.getSelectedItem();
					;
					if (Category.getSelectedItem().equals("Dresses")) {
						sql += " (`ClothesID`, `Length`, `Type`) VALUES ('" + clothesIdTxt.getText() + "', '"
								+ dressLengthTxt.getText() + "', '" + dressTypeTxt.getText() + "')";
					} else if (Category.getSelectedItem().equals("Tops")) {
						sql += " (`ClothesID`, `Sleeves`, `Collar`, `Size`, `Type`) VALUES ('" + clothesIdTxt.getText()
								+ "', '" + topSleevesTxt.getText() + "', '" + topCollarTxt.getText() + "', '"
								+ topSizeTxt.getText() + "', '" + topTypeTxt.getText() + "')";
					} else if (Category.getSelectedItem().equals("Hats")) {
						sql += "(`ClothesID`, `Size`, `Type`) VALUES ('" + clothesIdTxt.getText() + "', '"
								+ hatSizeTxt.getText() + "', '" + hatTypeTxt.getText() + "')";
					} else if (Category.getSelectedItem().equals("Shoes")) {
						sql += "(`ClothesID`, `Size`, `Type`, `Laces`) VALUES ('" + clothesIdTxt.getText() + "', '"
								+ shoesSizeTxt.getText() + "', '" + shoesTypeTxt.getText() + "', '"
								+ shoesLacesTxt.getText() + "')";
					} else if (Category.getSelectedItem().equals("Pants")) {
						sql += "(`ClothesID`, `Length`, `Fit`, `WaistSize`, `Type`, `Inseam`) VALUES ('"
								+ clothesIdTxt.getText() + "', '" + pantsLengthTxt.getText() + "', '"
								+ pantsFitTxt.getText() + "', '" + pantsWaistSizeTxt.getText() + "', '"
								+ pantssTypeTxt.getText() + "', '" + pantsInseamTxt.getText() + "')";
					}

					DatabaseRetrieval.executeUpdate(clothesSql);
					DatabaseRetrieval.executeUpdate(sql);
					JOptionPane.showMessageDialog(null, "Clothing item added", "Success", JOptionPane.OK_OPTION);
				} catch (Exception e1) {
					if (e1.getMessage().equals("Clothes Item Already Exists (Duplicate ClothesID)")) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} else {
						JOptionPane.showMessageDialog(null, "Fill out the necessary fields.");
					}
				}
				clothesIdTxt.setText("Clothes ID");
				ColorTxt.setText("Color");
				BrandTxt.setText("Brand");
				MaterialTxt.setText("Material");
				PriceTxt.setText("Price");
				GenderTxt.setText("Gender");
				dressLengthTxt.setText("Dress Length");
				dressTypeTxt.setText("Dress Type");
				hatSizeTxt.setText("Hat Size");
				hatTypeTxt.setText("Hat Type");
				pantsLengthTxt.setText("Pants Length");
				pantsFitTxt.setText("Pants Fit");
				pantsWaistSizeTxt.setText("Waist Size");
				pantssTypeTxt.setText("Pants Type");
				pantsInseamTxt.setText("Pants Inseam");
				shoesSizeTxt.setText("Shoe Size");
				shoesTypeTxt.setText("Shoe Type");
				shoesLacesTxt.setText("Shoe Laces");
				topSleevesTxt.setText("Top Sleeves");
				topCollarTxt.setText("Top Collar");
				topSizeTxt.setText("Top Size");
				topTypeTxt.setText("Top Type");
			}
		});

	}
}