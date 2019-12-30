<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format"
        xmlns:xi="http://www.w3.org/2001/XInclude"
        language="la_CL"
        font-size="7.5pt"
        line-height="1.4em"
        color="#000000">
    <fo:layout-master-set>
		 <fo:simple-page-master master-name="default" page-height="297mm" page-width="210mm">
            <fo:region-body region-name="boa-body" margin-top="20mm" margin-bottom="12mm" margin-left="10.4mm" margin-right="10.4mm"/>
			<fo:region-before region-name="boa-before"/>
        </fo:simple-page-master>
    </fo:layout-master-set>

	<fo:page-sequence master-reference="default" force-page-count="auto" hyphenate="true" hyphenation-character="-" hyphenation-push-character-count="1" hyphenation-remain-character-count="1">
		<fo:static-content flow-name="boa-before">
			<fo:block font-size="16pt" text-align="center" margin-top="10mm">${name}</fo:block>
		</fo:static-content>
		<fo:flow flow-name="boa-body">
			<#list tables as t>
			<fo:block page-break-after="always">
				<fo:table table-layout="fixed" width="100%" border-collapse="collapse" background-color="#EDEDF0">
					<#list 0..t.getCols()-1 as c>
					<fo:table-column column-width="${t.getColumnWeight(c)}%" />
					</#list>
					<#if t.hasCaptions()>
					<fo:table-header>
						<fo:table-row>
							<#list 0..t.getCols()-1 as c>
								<fo:table-cell font-size="9pt" line-height="1.6em" text-align="center" border-before-style="solid" border-after-style="solid" border-start-style="solid" border-end-style="solid">
									<fo:block>
										${t.getCaption(c)}
									</fo:block>
								</fo:table-cell>
							</#list>			
						</fo:table-row>
					</fo:table-header>		
					</#if>
					<fo:table-body>
						<#list 0..t.getRows()-1 as row>
						<fo:table-row>
							<#list 0..t.getCols()-1 as c>
							<fo:table-cell font-size="9pt" line-height="1.6em" text-align="center" border-before-style="solid" border-after-style="solid" border-start-style="solid" border-end-style="solid">
								<fo:block>${t.getValue(row,c)}</fo:block>
							</fo:table-cell>
							</#list>
						</fo:table-row>
						</#list>
					
					</fo:table-body>
				</fo:table>
			</fo:block>
			</#list>
		</fo:flow>
	</fo:page-sequence>
</fo:root>
