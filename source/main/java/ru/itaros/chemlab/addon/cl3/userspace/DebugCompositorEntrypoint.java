package ru.itaros.chemlab.addon.cl3.userspace;

import java.io.File;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class DebugCompositorEntrypoint {

	public static void main(String[] args) {
		
		System.out.println("ABDO COMPLETE THE FUCKING THAUMIC WARDEN ALREADY");
		
		ContractCollector collector = new ContractCollector();

		collector.genericItems=new UserspaceGenericItemContract[2];
		collector.genericItems[0] = new UserspaceGenericItemContract();
		collector.genericItems[1] = new UserspaceGenericItemContract();
		
		collector.genericItems[0].nodeName="ore.crushed.borax";
		collector.genericItems[1].nodeName="ore.crushed.bif";
		
		try {
			MarshallIn(collector);
		} catch (JAXBException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	private static void MarshallIn(ContractCollector collector) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(ContractCollector.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		File target = new File("dcdump.xml");
		marshaller.marshal(collector, target);
		System.out.println("File is written to: "+target.getAbsolutePath());
	}
	
	public static ContractCollector MarshallOut(InputStream stream) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(ContractCollector.class);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		ContractCollector collector = (ContractCollector)unmarshaller.unmarshal(stream);
		return collector;
	}
	

}
