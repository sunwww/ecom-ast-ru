<%@ page language="java" pageEncoding="UTF-8" contentType="application/opensearchdescription+xml; charset=UTF-8"%>
<?xml version="1.0" encoding="UTF-8"?>
 <OpenSearchDescription xmlns="http://a9.com/-/spec/opensearch/1.1/">
   <ShortName>Найти персону</ShortName>
   <Description>Поиск в МИАЦ</Description>
   <Tags>example web</Tags>
   <Contact>ecom@ecom-ast.ru</Contact>
<!--   <Url type="application/atom+xml"-->
<!--        template="http://example.com/?q={searchTerms}&amp;pw={startPage?}&amp;format=atom"/>-->
<!--   <Url type="application/rss+xml"-->
<!--        template="http://example.com/?q={searchTerms}&amp;pw={startPage?}&amp;format=rss"/>-->
<%
	String protocol = request.getProtocol().toLowerCase();
	protocol = protocol.substring(0, protocol.indexOf("/")).toLowerCase();
 %>
	<!-- <%=request.getContextPath() %> -->
   <Url type="text/html" 
        template="<%=protocol+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/mis_patients.do?lastname={searchTerms}" %>"/>
   <LongName>Поиск в МИАЦ</LongName>
<!--   <Image height="64" width="64" type="image/png">http://example.com/websearch.png</Image>-->
   <Image height="16" width="16" type="image/vnd.microsoft.icon">favicon.ico</Image>
<!--   <Query role="example" searchTerms="cat" />-->
   <Developer>ecom-ast.ru</Developer>
   <Attribution>
     Search in MIAC 
   </Attribution>
   <SyndicationRight>open</SyndicationRight>
   <AdultContent>false</AdultContent>
   <Language>ru-RU</Language>
   <OutputEncoding>UTF-8</OutputEncoding>
   <InputEncoding>UTF-8</InputEncoding>
 </OpenSearchDescription>
