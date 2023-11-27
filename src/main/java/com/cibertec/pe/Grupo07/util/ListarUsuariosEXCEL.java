package com.cibertec.pe.Grupo07.util;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.cibertec.pe.Grupo07.model.Usuario;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



@Component("usuarios.xlsx")
public class ListarUsuariosEXCEL extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition","attachment; filename=\"listado_usuarios.xlsx\"");
		Sheet hoja=workbook.createSheet("Usuarios");
		Row filaTitulo=hoja.createRow(0);
		Cell celda = filaTitulo.createCell(0);
		celda.setCellValue("LISTADO GENERAL DE USUARIOS");
		Row filaData=hoja.createRow(2);
		String []columns= {"NOMBRES","APELLIDO PATERNO","APELLIDO MATERNO","FECHA DE NACIMIENTO","EMAIL","DNI","TELEFONO"};
		for (int i = 0; i < columns.length; i++) {
			celda=filaData.createCell(i);
			celda.setCellValue(columns[i]);
		}
		
		@SuppressWarnings("unchecked")
		List<Usuario>listaU=(List<Usuario>) model.get("usuarios");
		
		int numFila=3;
		for (Usuario usuario : listaU) {
			filaData=hoja.createRow(numFila);
			filaData.createCell(0).setCellValue(usuario.getNombres());
			filaData.createCell(1).setCellValue(usuario.getPaterno());
			filaData.createCell(2).setCellValue(usuario.getMaterno());
			filaData.createCell(3).setCellValue(usuario.getFechaNacimiento());
			filaData.createCell(4).setCellValue(usuario.getEmail());
			filaData.createCell(5).setCellValue(usuario.getNumeroDocumento());
			filaData.createCell(6).setCellValue(usuario.getTelefono());
			
			numFila ++;
			
			
		}
		
	}

}