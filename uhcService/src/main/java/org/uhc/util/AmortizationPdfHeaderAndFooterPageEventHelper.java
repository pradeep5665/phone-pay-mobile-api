package org.uhc.util;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class AmortizationPdfHeaderAndFooterPageEventHelper extends PdfPageEventHelper {
	private static final Logger LOGGER = LogManager.getLogger(AmortizationPdfHeaderAndFooterPageEventHelper.class.getName());
	private long loanNum = 0;
	private static final String IMAGE_64  = "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"; 
	public void onStartPage(PdfWriter pdfWriter, Document document) {
	      Rectangle rect = pdfWriter.getBoxSize("rectangle");
	      
	      Image logo;
	      Chunk chunk=null;
		try {
			logo=Image.getInstance(addLogo());
			logo.setAlignment(Image.MIDDLE);
		      logo.scaleAbsoluteHeight(20);
		      logo.scaleAbsoluteWidth(20);
		      logo.scalePercent(100);
		      chunk = new Chunk(logo, 10, -35);
		} catch (BadElementException | IOException e) {
			LOGGER.error("logo path: Logo image path not found", e);
		}
	      document.setMargins(30, 30, 85, 80);
	      // TOP LEFT
	      ColumnText.showTextAligned(pdfWriter.getDirectContent(),
	               Element.ALIGN_RIGHT, new Phrase(chunk), rect.getLeft(),
	               rect.getTop(), 0);
	      Phrase phrase = new Phrase(String.format("Amortization Schedule for Loan "+loanNum),new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
	      ColumnText.showTextAligned(pdfWriter.getDirectContent(),
	               Element.ALIGN_LEFT, phrase,
	               rect.getRight() / 2-60, rect.getTop()-33, 0);
	  }
	 
	  public void onEndPage(PdfWriter pdfWriter, Document document) {
	      System.out.println("onEndPage() method > Writing footer in file");
	      Rectangle rect = pdfWriter.getBoxSize("rectangle");
	      String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm a"));
	    
	      
	      ColumnText.showTextAligned(pdfWriter.getDirectContent(),
	               Element.ALIGN_LEFT,new Phrase(String.valueOf("* Escrow payments change depending on the amounts paid for property taxes, homeowner's or flood insurance premiums, or escrow shortages/surpluses."), new Font(Font.FontFamily.UNDEFINED, 7)),
	               rect.getLeft()-150, rect.getBottom(), 0);
	      ColumnText.showTextAligned(pdfWriter.getDirectContent(),
	               Element.ALIGN_LEFT,new Phrase(String.valueOf("Schedule created "+localDateString+" Page "+pdfWriter.getPageNumber()), new Font(Font.FontFamily.UNDEFINED, 7)),
	               rect.getRight() / 2-60, rect.getBottom()-10, 0);
	  }
	public long getLoanNumber(long loanNumber) {
		loanNum = loanNumber;
		return loanNum;
	}
	public 	byte[] addLogo() {
		byte[] image = Base64.getDecoder().decode(IMAGE_64);
		return image;
	}
	
}
