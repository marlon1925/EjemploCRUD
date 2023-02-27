import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class v1 {
    private JTextField cedutaltxt;
    private JTextField textField2;
    private JComboBox comboBox1;
    private JPanel panel;
    private JButton crear;
    private JButton borrar;
    private JButton actualizar;
    private JButton buscar;

    Statement st;
    PreparedStatement ps;
    ResultSet rs;
    Connection con;
    public v1(){
        try{
            con = getConnection();
            st = con.createStatement();
            rs = st.executeQuery("SELECT MUSICA FROM prueba.musica");

            while (rs.next()){
                comboBox1.addItem(rs.getString("MUSICA"));
            }

        }catch (HeadlessException | SQLException f){
            System.out.println(f);
        }

        crear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                con = getConnection();
                try {
                    String musica = comboBox1.getSelectedItem().toString();

                    ps = con.prepareStatement("INSERT INTO prueba.persona(Cedula, Nombre, Musica) values (?,?,?)");

                    ps.setString(1, cedutaltxt.getText());
                    ps.setString(2, textField2.getText());
                    ps.setString(3, musica);

                    int res = ps.executeUpdate();
                    if(res > 0){
                        JOptionPane.showMessageDialog(null,"Se creo");
                        cedutaltxt.setText("");
                        textField2.setText("");
                    }else {
                        JOptionPane.showMessageDialog(null, "No se creo");
                    }
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });

        borrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                con = getConnection();
                try{
                    ps = con.prepareStatement("DELETE FROM prueba.persona WHERE CEDULA =" + cedutaltxt.getText());
                    int res = ps.executeUpdate();
                    if(res>0){
                        JOptionPane.showMessageDialog(null,"Se elimino");
                        cedutaltxt.setText("");
                        textField2.setText("");
                    }else {
                        JOptionPane.showMessageDialog(null,"No vale");
                    }
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });

        actualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                con = getConnection();

                try{
                    String musica = comboBox1.getSelectedItem().toString();
                    ps = con.prepareStatement("UPDATE prueba.persona set Nombre=?, Musica=? where Cedula =" + cedutaltxt.getText());

                    ps.setString(1,textField2.getText());
                    ps.setString(2, musica);

                    int res = ps.executeUpdate();

                    if(res > 0){
                        JOptionPane.showMessageDialog(null,"Si vale");
                    }else {
                        JOptionPane.showMessageDialog(null,"No vale");
                    }

                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });

        buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                con = getConnection();
                try{
                    st = con.createStatement();
                    rs = st.executeQuery("SELECT * from prueba.persona where Cedula = " + cedutaltxt.getText());

                    while (rs.next()){
                        cedutaltxt.setText(rs.getString("Cedula"));
                        textField2.setText(rs.getString("Nombre"));
                        comboBox1.setSelectedItem(rs.getString("Musica"));
                    }
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });
    }
    public static Connection getConnection() {
        Connection con = null;
        String base = "prueba";
        String url = "jdbc:mysql://localhost:3306/" + base;
        String user = "root";
        String password = "marlon";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
        }catch (ClassCastException | SQLException e){
            System.err.println(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return con;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Prueba");
        frame.setContentPane(new v1().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(300,300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
