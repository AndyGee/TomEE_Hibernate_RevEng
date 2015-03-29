package com.tomitribe.agumbrecht;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tomitribe.agumbrecht.application.BookService;
import com.tomitribe.agumbrecht.entities.Book;
import com.tomitribe.agumbrecht.service.InterceptedService;
import com.tomitribe.agumbrecht.service.ServiceApplication;

@RunWith(Arquillian.class)
public class InterceptedServiceTest extends Assert {

	/**
	 * 53 UTF-8 chars
	 */
	private static final String NAME1 = "üöäßÖÄÜ¡¢£¤¥¦§¨©ª«¬ ®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎ";

	/**
	 * ShrinkWrap is used to create a war file on the fly.
	 * <p/>
	 * The API is quite expressive and can build any possible flavor of war
	 * file. It can quite easily return a rebuilt war file as well.
	 * <p/>
	 * More than one @Deployment method is allowed.
	 */
	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(WebArchive.class).addClasses(ServiceApplication.class, BookService.class, Book.class
				, InterceptedService.class
				, com.tomitribe.reveng.dao.AddressEntity.class)
				.addPackage("com.tomitribe.reveng.dao").addAsResource("persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
	}

	/**
	 * This URL will contain the following URL data
	 * <p/>
	 * - http://<host>:<port>/<webapp>/
	 * <p/>
	 * This allows the test itself to be agnostic of server information or even
	 * the name of the webapp
	 */
	@ArquillianResource
	private URL url;

	@Resource
	private DataSource bookDatabase;

	@Inject
	private BookService bookService;

	@Test
	public void getBook() throws Exception {

		Book book = new Book();
		book.setBookTitle(NAME1);

		final int id = bookService.addBook(book);

		// First call caches stored
		book = getClient().path("api/intercept/" + id).get(Book.class);

		assertEquals("Invalid book id", id, book.getBookId());
		assertEquals("Invalid book name", NAME1, book.getBookTitle());

	}

	private WebClient getClient() throws URISyntaxException {
		return WebClient.create(url.toURI()).accept(MediaType.APPLICATION_JSON);
	}

	/**
	 * Reusable utility method Move to a shared class or replace with equivalent
	 */
	public static String slurp(final InputStream in) throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final byte[] buffer = new byte[1024];
		int length;
		while ((length = in.read(buffer)) != -1) {
			out.write(buffer, 0, length);
		}
		out.flush();
		return new String(out.toByteArray());
	}

}
