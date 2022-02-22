package org.uhc.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.uhc.controller.envelop.request.AmortizationPdfRequest;
import org.uhc.controller.envelop.response.AmortizationResponse;
import org.uhc.dao.dto.AmortizationPmtDto;
import org.uhc.dao.dto.FilePathDto;
import org.uhc.service.AmortizationService;
import org.uhc.util.AmortizationPdfHeaderAndFooterPageEventHelper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service 
public class AmortizationServiceImpl implements AmortizationService {
	private static final Logger LOGGER = LogManager.getLogger(AmortizationServiceImpl.class.getName());
	@Value("${server.download.amortizationPdfPath}")
	private StringBuffer amortizationPdfPath;
	
	@Value("${pdfDir}")
	private String pdfDir;
	
	@Value("${reportFileName}")
	private String reportFileName;
	
	@Value("${reportFileNameDateFormat}")
	private String reportFileNameDateFormat;
	
	@Value("${localDateFormat}")
	private String localDateFormat;
	
	@Value("${currencySymbol:}")
	private String currencySymbol;
	
	@Value("${table_noOfColumns}")
	private int noOfColumns;
	@Value("${originalSubject}")
	private String originalSubject;
	
	@Value("${monthlySubject}")
	private String monthlySubject;
	
	@Value("${yearlySubject}")
	private String yearlySubject;
	
	@Value("${oneTimeSubject}")
	private String oneTimeSubject;

	private String filePath = "";
	String tmpDirsLocation = "";
	String tmpdir = "";
	
	private  Set<FilePathDto> filePathAndDir = new HashSet<FilePathDto>();
	
	@Override
	public  ResponseEntity<Resource> getAmortizationGeneratePdfInfo(AmortizationPdfRequest pdfRequest) {

		AmortizationResponse response = new AmortizationResponse();
		 Document document = new Document();
		 HttpStatus status = HttpStatus.NO_CONTENT;
		 ResponseEntity<Resource> entity = new ResponseEntity<Resource>(status);
		 FilePathDto filePathDto = new FilePathDto();
		
		 try {
			
			 PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(getPdfNameWithDate(pdfRequest)));
			 Rectangle rectangle = new Rectangle(180, 30, 550, 800);
		      pdfWriter.setBoxSize("rectangle", rectangle);
		      AmortizationPdfHeaderAndFooterPageEventHelper headerAndFooterPageEventHelper = new AmortizationPdfHeaderAndFooterPageEventHelper();
		      headerAndFooterPageEventHelper.getLoanNumber(pdfRequest.getLoanNumber());
		      	pdfWriter.setPageEvent(headerAndFooterPageEventHelper);	
		      	document.open();
			 	createHeaderTable(document, 2,pdfRequest);
				createTable(document,noOfColumns, pdfRequest);
				document.close();
				File file = new File(filePath);
	            Resource fileSystemResource = new FileSystemResource(file);
	            entity = ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).body(fileSystemResource);
			} catch (Exception e) {
			             LOGGER.error("generate pdf: PDFException occurred generating", e);
				        	entity = ResponseEntity.noContent().build();
			         
			} 
		 filePathDto.setFilePath(filePath);
		 filePathDto.setPathDir(tmpdir);
		 filePathAndDir.add(filePathDto);
		 return entity;
	}
		private String getPdfNameWithDate(AmortizationPdfRequest pdfRequest) {
		
		try {
		
			tmpdir = Files.createTempDirectory("tmpDirPrefixPdf").toFile().getAbsolutePath();
			Path dir = Paths.get(tmpdir);
			tmpDirsLocation = dir.toString();
		
		} catch (IOException e) {
			LOGGER.info("getting error during create sub resource folder. ", e);
		}
		 String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(reportFileNameDateFormat));
		if(pdfRequest.getPaymentType().equalsIgnoreCase("Monthly")) {
			filePath = tmpDirsLocation+"/"+localDateString+"_MONTHLY_amortization.pdf";
		return filePath;
		}else if(pdfRequest.getPaymentType().equalsIgnoreCase("No Extra Principal")) {
			filePath = tmpDirsLocation+"/"+localDateString+"_ORIGINAL_amortization.pdf";
		return filePath;
		}else if(pdfRequest.getPaymentType().equalsIgnoreCase("One-Time")) {
			filePath = tmpDirsLocation+"/"+localDateString+"_ONE-TEIM_amortization.pdf";
		return filePath;
		}else{
		filePath = tmpDirsLocation+"/"+localDateString+"_YEARLY_amortization.pdf";
		return filePath;
	}
	}
	private static void leaveEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	
	private void createHeaderTable(Document document, int noOfHeadColumns, AmortizationPdfRequest pdfRequest) throws DocumentException {
		Paragraph paragraph = new Paragraph();
		leaveEmptyLine(paragraph, 3);
		document.add(paragraph);

				PdfPTable table = new PdfPTable(noOfHeadColumns);
				table.setWidthPercentage(70);
				float[] columnWidths = new float[]{60f, 20f};
				table.setWidths(columnWidths);
				
				PdfPTable table1 = new PdfPTable(noOfHeadColumns);
				table1.setWidthPercentage(70);
				float[] columnWidths1 = new float[]{60f, 20f};
				table1.setWidths(columnWidths1);
				
				PdfPTable table2 = new PdfPTable(noOfHeadColumns);
				table2.setWidthPercentage(70);
				float[] columnWidths2 = new float[]{60f, 20f};
				table2.setWidths(columnWidths2);
				
				PdfPTable table3 = new PdfPTable(noOfHeadColumns);
				table3.setWidthPercentage(70);
				float[] columnWidths3 = new float[]{60f, 20f};
				table3.setWidths(columnWidths3);

				PdfPTable table4 = new PdfPTable(noOfHeadColumns);
				table4.setWidthPercentage(70);
				float[] columnWidths4 = new float[]{60f, 20f};
				table4.setWidths(columnWidths4);
				
				PdfPTable table5 = new PdfPTable(noOfHeadColumns);
				table5.setWidthPercentage(70);
				float[] columnWidths5 = new float[]{60f, 20f};
				table5.setWidths(columnWidths5);

				PdfPTable table6 = new PdfPTable(1);
				table6.setWidthPercentage(70);
				table6.getDefaultCell().setBorderWidth(2);
				table6.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				table6.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
				
				
				PdfPTable table7 = new PdfPTable(noOfHeadColumns);
				table7.setWidthPercentage(70);
				float[] columnWidths7 = new float[]{50f, 40f};
				table7.setWidths(columnWidths7);
				
				
				PdfPTable table8 = new PdfPTable(3);
				table8.setWidthPercentage(70);
				float[] columnWidths8 = new float[]{22f, 24f,36};
				table8.setWidths(columnWidths8);
				
				PdfPTable table9 = new PdfPTable(3);
				table9.setWidthPercentage(70);
				float[] columnWidths9 = new float[]{22f, 24f,36};
				table9.setWidths(columnWidths9);
				
				PdfPTable table10 = new PdfPTable(3);
				table10.setWidthPercentage(70);
				float[] columnWidths10 = new float[]{22f, 24f,36};
				table10.setWidths(columnWidths10);
				
				PdfPTable table11 = new PdfPTable(3);
				table11.setWidthPercentage(70);
				float[] columnWidths11 = new float[]{22f, 24f,36};
				table11.setWidths(columnWidths11);
				
				PdfPTable table12 = new PdfPTable(3);
				table12.setWidthPercentage(70);
				float[] columnWidths12 = new float[]{27f, 19f,36};
				table12.setWidths(columnWidths12);
				
				PdfPTable table13 = new PdfPTable(1);
				table13.setWidthPercentage(70);
				table13.getDefaultCell().setBorderWidth(0);
				table13.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				table13.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
				
				PdfPTable table14 = new PdfPTable(1);
				table14.setWidthPercentage(70);
				table14.getDefaultCell().setBorderWidth(0);
				table14.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				table14.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
				
				PdfPTable table15 = new PdfPTable(1);
				table15.setWidthPercentage(70);
				table15.getDefaultCell().setBorderWidth(0);
				table15.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				table15.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
				
				PdfPTable table16 = new PdfPTable(noOfHeadColumns);
				table16.setWidthPercentage(70);
				float[] columnWidths16 = new float[]{50f, 40f};
				table16.setWidths(columnWidths16);
				Font font  = new Font(FontFamily.UNDEFINED, 10, Font.NORMAL);
				Paragraph p = new Paragraph("Ammount Borrowed",font);
	            PdfPCell cell = new PdfPCell(p);
	            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            
	            Paragraph p1 = new Paragraph(pdfRequest.getAmmountBorrowed(),font);
	            PdfPCell cell1 = new PdfPCell(p1);
	            cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            table.addCell(cell).setBorder(0);
	            table.addCell(cell1).setBorder(0);
				
				
	            Paragraph p2 = new Paragraph("Payback Periods", font);
	            PdfPCell cell2 = new PdfPCell(p2);
	            cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
	            
	            Paragraph p3 = new Paragraph(pdfRequest.getPaybackPeriods(), font);
	            PdfPCell cell3 = new PdfPCell(p3);
	            cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            table1.addCell(cell2).setBorder(0);
	            table1.addCell(cell3).setBorder(0);
	            
	            Paragraph p4 = new Paragraph("Interest Rate", font);
	            PdfPCell cell4 = new PdfPCell(p4);
	            cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
	            
	            Paragraph p5 = new Paragraph(pdfRequest.getInterestRate(), font);
	            PdfPCell cell5 = new PdfPCell(p5);
	            cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            table2.addCell(cell4).setBorder(0);
	            table2.addCell(cell5).setBorder(0);
	            
	            Paragraph p6 = new Paragraph("Maturity Date", font);
	            PdfPCell cell6 = new PdfPCell(p6);
	            cell6.setHorizontalAlignment(Element.ALIGN_LEFT);
	            
	            Paragraph p7 = new Paragraph(pdfRequest.getMaturityDate(), font);
	            PdfPCell cell7 = new PdfPCell(p7);
	            cell7.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            table3.addCell(cell6).setBorder(0);
	            table3.addCell(cell7).setBorder(0);
	            
	            Paragraph p8 = new Paragraph("Currnet Unpaid Principal Balance", font);
	            PdfPCell cell8 = new PdfPCell(p8);
	            cell8.setHorizontalAlignment(Element.ALIGN_LEFT);
	            
	            Paragraph p9 = new Paragraph(pdfRequest.getCurrentUnpaiPrincipleBalance(), font);
	            PdfPCell cell9 = new PdfPCell(p9);
	            cell9.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            table4.addCell(cell8).setBorder(0);
	            table4.addCell(cell9).setBorder(0);
	            
	            Paragraph p10 = new Paragraph("Next Payment Due Date", font);
	            PdfPCell cell10 = new PdfPCell(p10);
	            cell10.setHorizontalAlignment(Element.ALIGN_LEFT);
	            
	            Paragraph p11 = new Paragraph(pdfRequest.getNextPaymentDue(), font);
	            PdfPCell cell11 = new PdfPCell(p11);
	            cell11.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            table5.addCell(cell10).setBorder(0);
	            table5.addCell(cell11).setBorder(0);
				
				Font bold  = new Font(FontFamily.UNDEFINED, 10, Font.BOLD);
				Phrase pr = new Phrase(pdfRequest.getTitle(), bold);
				table6.addCell(pr);
				
				//Font bold1  = new Font(FontFamily.HELVETICA, 11, Font.BOLD);
				Phrase pr1 = new Phrase("Current", bold);
				PdfPCell cell12 = new PdfPCell(pr1);
	            cell12.setHorizontalAlignment(Element.ALIGN_RIGHT);
				Phrase pr2 = new Phrase("Modified", bold);
				PdfPCell cell13 = new PdfPCell(pr2);
	            cell13.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table7.addCell(cell12).setBorderWidth(0);
				table7.addCell(cell13).setBorderWidth(0);
				
				Paragraph pg0 = new Paragraph("Principal & Interest", font);
	            PdfPCell cellt0 = new PdfPCell(pg0);
	            cellt0.setHorizontalAlignment(Element.ALIGN_LEFT);
	            
	            Paragraph pg01 = new Paragraph(pdfRequest.getPrincipalAndInterest(), font);
	            PdfPCell cellt01 = new PdfPCell(pg01);
	            cellt01.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            
	            Paragraph pg02 = new Paragraph(pdfRequest.getPrincipalAndInterestMod(), font);
	            PdfPCell cellt02 = new PdfPCell(pg02);
	            cellt02.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            
	            table8.addCell(cellt0).setBorderWidth(0);
	            table8.addCell(cellt01).setBorderWidth(0);
	            table8.addCell(cellt02).setBorderWidth(0);
	            
	            Paragraph pg1 = new Paragraph("Escrow Payment*", font);
	            PdfPCell cellt1 = new PdfPCell(pg1);
	            cellt1.setHorizontalAlignment(Element.ALIGN_LEFT);
	            
	            Paragraph pg2 = new Paragraph(pdfRequest.getEscrowPayment(), font);
	            PdfPCell cellt2 = new PdfPCell(pg2);
	            cellt2.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            
	            Paragraph pg3 = new Paragraph(pdfRequest.getEscrowPaymentMod(), font);
	            PdfPCell cellt3 = new PdfPCell(pg3);
	            cellt3.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            
	            table9.addCell(cellt1).setBorderWidth(0);
	            table9.addCell(cellt2).setBorderWidth(0);
	            table9.addCell(cellt3).setBorderWidth(0);
	            
	            
	            Paragraph pg4 = new Paragraph("Montly Payment", font);
	            PdfPCell cellt4 = new PdfPCell(pg4);
	            cellt4.setHorizontalAlignment(Element.ALIGN_LEFT);
	            
	            Paragraph pg5 = new Paragraph(pdfRequest.getMonthlyPayment(),font);
	            PdfPCell cellt5 = new PdfPCell(pg5);
	            cellt5.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            
	            Paragraph pg6 = new Paragraph(pdfRequest.getMonthlyPaymentMod(), font);
	            PdfPCell cellt6 = new PdfPCell(pg6);
	            cellt6.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            
	            table10.addCell(cellt4).setBorderWidth(0);
	            table10.addCell(cellt5).setBorderWidth(0);
	            table10.addCell(cellt6).setBorderWidth(0);
	            
	            Paragraph pg7 = new Paragraph("Adjusted Pay Off", font);
	            PdfPCell cellt7 = new PdfPCell(pg7);
	            cellt7.setHorizontalAlignment(Element.ALIGN_LEFT);
	            
	            Paragraph pg8 = new Paragraph(pdfRequest.getAnticipatedPayOff(), font);
	            PdfPCell cellt8 = new PdfPCell(pg8);
	            cellt8.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            
	            Paragraph pg9 = new Paragraph(pdfRequest.getAnticipatedPayOffMod(), font);
	            PdfPCell cellt9 = new PdfPCell(pg9);
	            cellt9.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            
	            table11.addCell(cellt7).setBorderWidth(0);
	            table11.addCell(cellt8).setBorderWidth(0);
	            table11.addCell(cellt9).setBorderWidth(0);
	            
	            Paragraph pg10 = new Paragraph("Remaning Interest Paid", font);
	            PdfPCell cellt10 = new PdfPCell(pg10);
	            cellt10.setHorizontalAlignment(Element.ALIGN_LEFT);
	            
	            Paragraph pg11 = new Paragraph(pdfRequest.getRemainingInterestPaid(), font);
	            PdfPCell cellt11 = new PdfPCell(pg11);
	            cellt11.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            
	            Paragraph pg12 = new Paragraph(pdfRequest.getRemainingInterestPaidMod(), font);
	            PdfPCell cellt12 = new PdfPCell(pg12);
	            cellt12.setHorizontalAlignment(Element.ALIGN_RIGHT);
	            
	            table12.addCell(cellt10).setBorderWidth(0);
	            table12.addCell(cellt11).setBorderWidth(0);
	            table12.addCell(cellt12).setBorderWidth(0);
	            
	            Phrase ph1 = new Phrase("Interest Saved", bold);
				PdfPCell cellp1 = new PdfPCell(ph1);
	            cellp1.setHorizontalAlignment(Element.ALIGN_RIGHT);
				Phrase ph2 = new Phrase(pdfRequest.getInterestSaved(), bold);
				PdfPCell cellp2 = new PdfPCell(ph2);
	            cellp2.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table16.addCell(cellp1).setBorderWidth(0);
				table16.addCell(cellp2).setBorderWidth(0);
	        
				Phrase p16 = new Phrase("The amortization schedule begins with your current unpaid principal balance",font);
				Phrase p17 = new Phrase("and your next payment due month.",font);
	            table13.addCell(p16);
				table14.addCell(p17);
				
				Phrase p15 = new Phrase("It does not represent an actual loan pay off.", bold);
				table15.addCell(p15);
				
				
		   document.add(table);
		   document.add(table1);
		   document.add(table2);
		   document.add(table3);
		   document.add(table4);
		   document.add(table5);
		   document.add(new Paragraph("                                       "));
		   document.add(table6);
		   document.add(new Paragraph("                                       "));
		   document.add(table7);
		   document.add(table8);
		   document.add(table9);
		   document.add(table10);
		   document.add(table11);
		   document.add(table12);
		   if(!pdfRequest.getInterestSaved().equals("")) {
		   document.add(table16);
		   }
		   document.add(new Paragraph("                                       "));
		   document.add(table13);
		   document.add(table14);
		   document.add(table15);
	}
	
	private void createTable(Document document, int noOfColumns, AmortizationPdfRequest pdfRequest) throws DocumentException {
		document.add(new Paragraph("                                       "));
		PdfPTable table = new PdfPTable(noOfColumns);
		table.setWidthPercentage(70);
		float[] columnWidths = new float[]{15f, 20f, 15f, 15f , 15f};
		table.setWidths(columnWidths);
		ArrayList<String> columnNames1 = new ArrayList<String>();
		columnNames1.add("Due Date");
		columnNames1.add("Payment Amt");
		columnNames1.add("Interest");
		columnNames1.add("Principal");
		columnNames1.add("Balance");
		for(int i=0; i<noOfColumns; i++) {
			System.out.println("columnNames : "+columnNames1.get(i));
			Font font = new Font(FontFamily.UNDEFINED,10, Font.NORMAL);
			font.setColor(BaseColor.DARK_GRAY);
			PdfPCell cell = new PdfPCell(new Phrase(columnNames1.get(i),font));
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			
			table.addCell(cell);
		}

		table.setHeaderRows(1);
		getDbData(table, pdfRequest, document);
		document.add(table);
		
	}
private void getDbData(PdfPTable table,  AmortizationPdfRequest pdfRequest,Document document) {
		List<AmortizationPmtDto> pay = new ArrayList<AmortizationPmtDto>();
		pay = pdfRequest.getPmtDtos();
		for (AmortizationPmtDto amortizationPmtDto : pay) {
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			
			Font font = new Font(FontFamily.UNDEFINED, 10, Font.NORMAL);
			font.setColor(BaseColor.DARK_GRAY);
			Phrase phrase = new Phrase(amortizationPmtDto.getDueDate(),font);
			Phrase phrase1 = new Phrase(amortizationPmtDto.getPaymentAmt(),font);
			Phrase phrase2 = new Phrase(amortizationPmtDto.getInterest(),font);
			Phrase phrase3 = new Phrase(amortizationPmtDto.getPrincipal(),font);
			Phrase phrase4 = new Phrase(amortizationPmtDto.getBalance(),font);
			table.addCell(phrase);
			table.addCell(phrase1);
			table.addCell(phrase2);
			table.addCell(phrase3);
			table.addCell(phrase4);
		}
		try {
			document.add(new Paragraph("                                       "));
		} catch (DocumentException e) {
			LOGGER.info("space between line in pdf", e);
		}
		
	}
@Scheduled(cron = "${uhc.removeAmorticationPDFFile.cronExpression}", zone = "America/Denver")
public void getPath() {
	if(filePathAndDir.size()!=0) {
	for(FilePathDto dto  : filePathAndDir) {
		try {
			Files.delete(Paths.get(dto.getFilePath()));
			Files.delete(Paths.get(dto.getPathDir()));
			LOGGER.info("amortization pdf file is deleted");

		} catch (Exception e) {
			LOGGER.info("amortization pdf file is not deleted ", e);
		}
	}
	}
	filePathAndDir = new HashSet<FilePathDto>(); 
}

}
