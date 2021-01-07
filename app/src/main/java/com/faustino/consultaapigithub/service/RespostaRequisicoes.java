package com.faustino.consultaapigithub.service;

import com.faustino.consultaapigithub.model.Item;

import java.util.List;

/**
 * //Essa interface será implementada para fornecer na origem das chamadas, o retorno das requisições, quando existirem
 *
 */

public interface RespostaRequisicoes {

    void onReceiveRepositorios(List<Item> listRepositorios);

}
