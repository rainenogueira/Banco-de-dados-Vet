package crud;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import model.DAO;
import utils.Validador;

public class PrimeiraTela extends JFrame {

    private static final long serialVersionUID = 1L;

    DAO dao = new DAO();
    private Connection con;
    private PreparedStatement pst;

    private JPanel contentPane;
    private JTextField txtPaciente;
    private JTextField txtTutor;
    private JTextField txtTelefone;
    private JTextField txtCpf;
    private JTable table;
    private JTextField txtReg;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                PrimeiraTela frame = new PrimeiraTela();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public PrimeiraTela() {
        
    	addWindowListener(new WindowAdapter() {
    	    @Override
    	    public void windowActivated(WindowEvent e) {
    	        status();
    	        carregarDadosTabela();
    	    }
    	});


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 679, 548);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitulo = new JLabel("Clinica Veterinária");
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblTitulo.setBounds(242, 21, 195, 40);
        contentPane.add(lblTitulo);

        criarCamposFormulario();
        criarBotoes();
        criarTabela();
    }

    private void criarCamposFormulario() {
        JLabel lblPaciente = new JLabel("Nome Paciente:");
        lblPaciente.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPaciente.setBounds(31, 144, 141, 13);
        contentPane.add(lblPaciente);

        txtPaciente = new JTextField();
        txtPaciente.setBounds(189, 144, 150, 19);
        txtPaciente.setDocument(new Validador(30));
        contentPane.add(txtPaciente);

        JLabel lblTutor = new JLabel("Nome Tutor:");
        lblTutor.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTutor.setBounds(31, 174, 141, 13);
        contentPane.add(lblTutor);

        txtTutor = new JTextField();
        txtTutor.setBounds(189, 174, 150, 19);
        txtTutor.setDocument(new Validador(50));
        contentPane.add(txtTutor);

        JLabel lblTelefone = new JLabel("Telefone Tutor:");
        lblTelefone.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblTelefone.setBounds(31, 204, 141, 13);
        contentPane.add(lblTelefone);

        txtTelefone = new JTextField();
        txtTelefone.setBounds(189, 204, 150, 19);
        txtTelefone.setDocument(new Validador(11));
        contentPane.add(txtTelefone);

        JLabel lblCpf = new JLabel("CPF Tutor:");
        lblCpf.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblCpf.setBounds(31, 234, 141, 13);
        contentPane.add(lblCpf);

        txtCpf = new JTextField();
        txtCpf.setBounds(189, 234, 150, 19);
        txtCpf.setDocument(new Validador(11));
        contentPane.add(txtCpf);
        
        txtReg = new JTextField();
        txtReg.setColumns(10);
        txtReg.setBounds(189, 114, 150, 19);
        contentPane.add(txtReg);
        txtReg.setDocument(new Validador(6));

    }

    private void criarBotoes() {
        JButton btnAdd = new JButton("Adicionar");
        btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnAdd.setBounds(40, 276, 116, 29);
        btnAdd.addActionListener(e -> adicionarPaciente());
        contentPane.add(btnAdd);

        JButton btnBusca = new JButton("Buscar");
        btnBusca.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnBusca.setBounds(508, 276, 116, 29);
        btnBusca.addActionListener(e -> buscarPaciente());
        contentPane.add(btnBusca);

        JButton btnAlt = new JButton("Alterar");
        btnAlt.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnAlt.setBounds(196, 276, 116, 29);
        btnAlt.addActionListener(e -> alterarPaciente());
        contentPane.add(btnAlt);

        JButton btnDelete = new JButton("Deletar");
        btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnDelete.setBounds(352, 276, 116, 29);
        btnDelete.addActionListener(e -> deletarPaciente());
        contentPane.add(btnDelete);
    }

    private void criarTabela() {
        JPanel panel = new JPanel();
        panel.setBounds(31, 315, 610, 175);
        contentPane.add(panel);

        table = new JTable();
        table.setModel(new DefaultTableModel(
        	    new Object[][] {},
        	    new String[] {"Registro", "Paciente", "Tutor", "Telefone", "CPF"}
        	));
        	JScrollPane scrollPane = new JScrollPane(table);
        	scrollPane.setBounds(31, 315, 610, 175);
        	contentPane.add(scrollPane);
        
        JLabel lblRegPaciente = new JLabel("Reg. Paciente");
        lblRegPaciente.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblRegPaciente.setBounds(31, 114, 141, 13);
        contentPane.add(lblRegPaciente);
    }

    private void adicionarPaciente() {
        try {
            con = dao.conectar();
            String sql = "INSERT INTO pacientes (nomePaciente, nomeTutor, telefone, cpfTutor) VALUES (?, ?, ?, ?)";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtPaciente.getText());
            pst.setString(2, txtTutor.getText());
            pst.setString(3, txtTelefone.getText());
            pst.setString(4, txtCpf.getText());

            int resultado = pst.executeUpdate();
            if (resultado == 1) {
                JOptionPane.showMessageDialog(this, "Paciente Registrado!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Falha no registro. Tente novamente.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro: " + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void buscarPaciente() {
        try {
            con = dao.conectar();
            String sql = "SELECT * FROM pacientes WHERE regPaciente = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtReg.getText());
            var rs = pst.executeQuery();

            if (rs.next()) {
                txtPaciente.setText(rs.getString("nomePaciente"));
                txtTutor.setText(rs.getString("nomeTutor"));
                txtTelefone.setText(rs.getString("telefone"));
                txtCpf.setText(rs.getString("cpfTutor"));
            } else {
                JOptionPane.showMessageDialog(this, "Paciente não encontrado.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar paciente: " + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void alterarPaciente() {
        try {
            con = dao.conectar();
            String sql = "UPDATE pacientes SET nomePaciente = ?, nomeTutor = ?, telefone = ?, cpfTutor = ? WHERE regPaciente = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtPaciente.getText());
            pst.setString(2, txtTutor.getText());
            pst.setString(3, txtTelefone.getText());
            pst.setString(4, txtCpf.getText());
            pst.setString(5, txtReg.getText());

            int resultado = pst.executeUpdate();
            if (resultado == 1) {
                JOptionPane.showMessageDialog(this, "Paciente atualizado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao atualizar paciente.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao alterar paciente: " + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void deletarPaciente() {
        try {
            con = dao.conectar();
            String sql = "DELETE FROM pacientes WHERE regPaciente = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, txtReg.getText());

            int resultado = pst.executeUpdate();
            if (resultado == 1) {
                JOptionPane.showMessageDialog(this, "Paciente deletado com sucesso!");
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao deletar paciente.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao deletar paciente: " + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    private void carregarDadosTabela() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Limpar a tabela antes de carregar novos dados

        try {
            con = dao.conectar();
            String sql = "SELECT regPaciente, nomePaciente, nomeTutor, telefone, cpfTutor FROM pacientes";
            pst = con.prepareStatement(sql);
            var rs = pst.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("regPaciente"),
                    rs.getString("nomePaciente"),
                    rs.getString("nomeTutor"),
                    rs.getString("telefone"),
                    rs.getString("cpfTutor")
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar tabela: " + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    private void limparCampos() {
        txtPaciente.setText("");
        txtTutor.setText("");
        txtTelefone.setText("");
        txtCpf.setText("");
    }

    private void status() {
        try {
            con = dao.conectar();
            if (con != null) {
                System.out.println("Banco de Dados conectado.");
            } else {
                System.out.println("Erro de conexão.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
