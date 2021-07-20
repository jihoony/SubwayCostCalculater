package com.myprj.subwaycost.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.myprj.subwaycost.domain.dto.XmlParserObject;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;

@Slf4j
public class FeignResultDecoder implements Decoder {

    private XmlMapper xmlMapper = new XmlMapper();

    @Override
    public XmlParserObject decode(Response response, Type type) throws IOException, FeignException {

        if (response.body() == null){
            throw new DecodeException(response.status(), "No valid data returned", response.request());
        }

        String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));

        XmlParserObject xmlParserObject = null;

        try {
            xmlParserObject = xmlMapper.readValue(bodyStr, XmlParserObject.class);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw e;
        }

        return xmlParserObject;
    }
}
