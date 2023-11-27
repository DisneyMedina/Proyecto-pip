package com.cibertec.pe.Grupo07.util;

import java.awt.Color;
import java.util.List;

import java.util.Map;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.cibertec.pe.Grupo07.model.Usuario;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("usuarios")
public class ListarUsuarioPDF extends AbstractPdfView{
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		@SuppressWarnings("unchecked")
		List<Usuario>listadoUsuario=(List<Usuario>) model.get("usuarios");
		Font fuenteTitulo=FontFactory.getFont(FontFactory.HELVETICA_BOLD,20,Color.WHITE);
		Font fuenteTituloColumnas = FontFactory.getFont(FontFactory.HELVETICA_BOLD,12,Color.WHITE);
		Font fuenteDataCeldas = FontFactory.getFont(FontFactory.COURIER,10,Color.BLACK);

		document.setPageSize(PageSize.LETTER.rotate());
		document.setMargins(-20,-20,40,20);
		document.open();
		PdfPTable tablaTitulo = new PdfPTable(1);
		PdfPCell celda=null;
		
		celda=new PdfPCell(new Phrase("LISTADO GENERAL DE USUARIOS",fuenteTitulo));
		celda.setBorder(0);
		celda.setBackgroundColor(new Color(0,124,141));
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(30);
		
		tablaTitulo.addCell(celda);
		tablaTitulo.setSpacingAfter(30);
		
		/*TABLA PARA MOSTRAR LISTADO DE CLIENTES*/
		PdfPTable tablaClientes = new PdfPTable(7);
		
		
		celda = new PdfPCell(new Phrase("NOMBRES",fuenteTituloColumnas));
		celda.setBackgroundColor(Color.lightGray);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaClientes.addCell(celda);
		
		celda = new PdfPCell(new Phrase("APELLIDO PATERNO",fuenteTituloColumnas));
		celda.setBackgroundColor(Color.lightGray);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaClientes.addCell(celda);
		
		celda = new PdfPCell(new Phrase("APELLIDO MATERNO",fuenteTituloColumnas));
		celda.setBackgroundColor(Color.lightGray);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaClientes.addCell(celda);
		
		celda = new PdfPCell(new Phrase("FECHA DE NACIMIENTO",fuenteTituloColumnas));
		celda.setBackgroundColor(Color.lightGray);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaClientes.addCell(celda);
		
		celda = new PdfPCell(new Phrase("EMAIL",fuenteTituloColumnas));
		celda.setBackgroundColor(Color.lightGray);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaClientes.addCell(celda);
		
		celda = new PdfPCell(new Phrase("DNI",fuenteTituloColumnas));
		celda.setBackgroundColor(Color.lightGray);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaClientes.addCell(celda);
		
		celda = new PdfPCell(new Phrase("TELEFONO",fuenteTituloColumnas));
		celda.setBackgroundColor(Color.lightGray);
		celda.setHorizontalAlignment(Element.ALIGN_CENTER);
		celda.setVerticalAlignment(Element.ALIGN_CENTER);
		celda.setPadding(10);
		tablaClientes.addCell(celda);
		
		
		
		/*BUCLE FOR*/
		
		for (Usuario usuario : listadoUsuario) {
			
			
			celda=new PdfPCell(new Phrase(usuario.getNombres().toString(),fuenteDataCeldas));
			celda.setPadding(5);
			tablaClientes.addCell(celda);

			
			celda=new PdfPCell(new Phrase(usuario.getPaterno().toString(),fuenteDataCeldas));
			celda.setPadding(5);
			tablaClientes.addCell(celda);
			
			celda=new PdfPCell(new Phrase(usuario.getMaterno().toString(),fuenteDataCeldas));
			celda.setPadding(5);
			tablaClientes.addCell(celda);
			
			celda=new PdfPCell(new Phrase(usuario.getFechaNacimiento().toString(),fuenteDataCeldas));
			celda.setPadding(5);
			tablaClientes.addCell(celda);
			
			celda=new PdfPCell(new Phrase(usuario.getEmail().toString(),fuenteDataCeldas));
			celda.setPadding(5);
			tablaClientes.addCell(celda);
			
			celda=new PdfPCell(new Phrase(usuario.getNumeroDocumento().toString(),fuenteDataCeldas));
			celda.setPadding(5);
			tablaClientes.addCell(celda);
			
			celda=new PdfPCell(new Phrase(usuario.getTelefono().toString(),fuenteDataCeldas));
			celda.setPadding(5);
			tablaClientes.addCell(celda);
			
			
		}
		
		/*ANEXAMOS LAS TABLAS DEL DOCUMENTO*/
		document.add(tablaTitulo);
		document.add(tablaClientes);
		
		
		
}

}
