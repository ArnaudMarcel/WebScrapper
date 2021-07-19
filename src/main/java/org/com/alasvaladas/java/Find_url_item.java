/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.com.alasvaladas.java;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author agenesi
 */
public class Find_url_item {

    private final java.util.Set<String> _all_url;
    
    public Find_url_item(String htmlContent){
        java.util.regex.Pattern htmlPattern = java.util.regex.Pattern.compile("\"sUrlPageProduit\":\"https://[[a-z][A-Z][0-9][.][-][/]]*\"", java.util.regex.Pattern.DOTALL);
        this._all_url = new java.util.HashSet();
        java.util.regex.Matcher htmlMatcher = htmlPattern.matcher(htmlContent);
        while (htmlMatcher.find()) {
            _all_url.add(htmlMatcher.group(0).replace("\"sUrlPageProduit\":\"", "").replace("\"",""));
        }
    }
    
    public java.util.Set<String> get_all_url(){
        return this._all_url;
    }
}