//import {Leclerc_items} from './Leclerc_items.js';

window.document.onreadystatechange = function () { // Called *TWO TIMES*: when "interactive" and later on... when "complete"
  if (window.document.readyState === "interactive") {
    window.console.log("DOM just loaded...");
    window.document.getElementById('name_product').addEventListener('click', Sought_item._seek); //Register the request
    window.document.getElementById('get_name_prod').addEventListener('keyup', event => {
      if(event.key === "Enter") Sought_item._seek(event);
    });
  }
};

class Sought_item {

  static _seek(event){
    //console.log(event.target.id);
    let data = {
      search_type : event.target.id,
      value : window.document.getElementById('get_name_prod').value,
    }
    Leclerc_items._send_data(data);
  }
}
