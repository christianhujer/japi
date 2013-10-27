<?xml version="1.0" encoding="utf-8"?>
<!--
  - $Id: cleanupXhtml11.xslt,v 1.2 2006/01/24 21:13:35 christianhujer Exp $
  -
  - Transformation cleaning up XHTML:
  - * removes superfluous namespace declarations
  - * removes attributes with default values
  -
  - © 2003 Christian W. Hujer
  -
  - Log: see end of file
  -->

<xsl:transform
    version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:html="http://www.w3.org/1999/xhtml"
    xmlns:helper="java:com.hujer.xpath.Helper"
>

    <xsl:output
        doctype-public="-//W3C//DTD XHTML 1.1//EN"
        doctype-system="http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd"
        encoding="utf-8"
        indent="no"
        method="xml"
        omit-xml-declaration="no"
        cdata-section-elements="html:script html:style"
    />

    <xsl:strip-space
        elements="html:body html:head html:html html:title html:h1 html:h2 html:h3 html:h4 html:h5 html:h6 html:address html:blockquote html:div html:p html:pre html:dl html:dt html:dd html:ol html:ul html:li html:applet html:form html:fieldset html:caption html:table html:td html:th html:tr html:col html:colgroup html:tbody html:thead html:tfoot html:object html:frameset html:param html:img html:frame html:noframes html:iframe html:meta html:link html:input html:select html:optgroup html:option html:button html:label html:legend"
    />

    <xsl:template match="html:head/@profile"/>
    <xsl:template match="html:html/@version"/>
    <xsl:template match="html:meta[@scheme='CVS']"/>
    <xsl:template match="html:td/@colspan[.='1']|html:td/@rowspan[.='1']|html:th/@colspan[.='1']|html:th/@rowspan[.='1']"/>
    <xsl:template match="html:a/@shape[.='rect']"/>
    <xsl:template match="html:input/@type[.='text']"/>
    <xsl:template match="html:pre/@xml:space[.='preserve']"/>

    <xsl:template match="@*">
        <xsl:copy/>
    </xsl:template>

    <xsl:template match="html:div[not(@*)]|html:span[not(@*)]">
        <xsl:apply-templates/>
    </xsl:template>

    <xsl:template match="html:*">
        <xsl:element
            name="{local-name()}"
            namespace="http://www.w3.org/1999/xhtml"
        >
            <xsl:apply-templates select="@*"/>
            <xsl:apply-templates/>
        </xsl:element>
    </xsl:template>

    <xsl:template match="comment()"/>

    <xsl:template match="/processing-instruction('doc')|/processing-instruction('IS10744')"/>
    <xsl:template match="processing-instruction()">
        <xsl:copy/>
    </xsl:template>

    <xsl:template match="html:*[@xml:space='preserve']//text()">
        <xsl:copy/>
    </xsl:template>

    <!-- New: Remove xml:base attributes introduced by XInclude -->
    <xsl:template match="@xml:base"/>

    <!--<xsl:template match="@xmlns:*"/>-->

    <!--xsl:template match="text()"-->
        <!--xsl:value-of select="helper:normalize(., preceding::node()[1], following::node()[1], parent::node())"/-->
        <!--xsl:choose>
            <xsl:when test=". = ' '"><xsl:text> </xsl:text></xsl:when>
            <xsl:otherwise>
                <xsl:if test="starts-with(., ' ') or starts-with(., '&#xA;')"><xsl:text> </xsl:text></xsl:if>
                <xsl:value-of select="normalize-space(.)"/>
                <xsl:if test="ends-with(., ' ') or ends-with(., '&#xA;')"><xsl:text> </xsl:text></xsl:if>
                <xsl:if test="following-sibling::* and substring(., string-length(.)) = ' ' or substring(., string-length(.)) = '&#xA;'"><xsl:text> </xsl:text></xsl:if>
            </xsl:otherwise>
        </xsl:choose-->
    <!--/xsl:template-->

</xsl:transform>
