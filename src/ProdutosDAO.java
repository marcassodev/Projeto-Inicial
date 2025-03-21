/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;


public class ProdutosDAO {
    
    Connection conn;
    
    public int cadastrarProduto (ProdutosDTO pro){
                
         int status = 0;
            try {
            conn = new conectaDAO().connectDB();

            String sql = "INSERT INTO produtos (nome, valor, status) VALUES(?, ?, ?)";
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setString(1, pro.getNome());
                st.setInt(2, pro.getValor());
                st.setString(3, pro.getStatus());

                status = st.executeUpdate();
            }

        } catch (SQLException ex) {
            System.out.println("Erro ao cadastrar Produto: " + ex.getMessage());
            return ex.getErrorCode();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return status;
    }
    
    public ArrayList<ProdutosDTO> listarProdutos() throws SQLException{
        
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();
        
        try (Connection conn = new conectaDAO().connectDB()) {
        String sql = "SELECT * FROM produtos";
        PreparedStatement stmt = conn.prepareStatement(sql);
    
        try (ResultSet rs = stmt.executeQuery()){
        while (rs.next()) {
            ProdutosDTO pro = new ProdutosDTO();
            pro.setId(rs.getInt("id"));
            pro.setNome(rs.getString("nome"));
            pro.setValor(rs.getInt("valor"));
            pro.setStatus(rs.getString("status"));
            listagem.add(pro);
                }
            } 
        }   catch (SQLException ex) {
        System.out.println("Erro ao listar produtos: " + ex.getMessage());
        throw ex;
        }
        return listagem;
 }
    public int venderProduto(int idProduto) {
    int status = 0;
    try {
        conn = new conectaDAO().connectDB();

        String sql = "UPDATE produtos SET status = ? WHERE id = ?";
        
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, "Vendido"); 
            st.setInt(2, idProduto);      

            status = st.executeUpdate();
        }

    } catch (SQLException ex) {
        System.out.println("Erro ao vender Produto: " + ex.getMessage());
        return ex.getErrorCode();
    } finally {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
    return status;
}
}    