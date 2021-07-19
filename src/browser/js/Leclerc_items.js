//import {seek_item} from 'seek_item.js';
'use strict'

class Leclerc_items {

  constructor(){ //used at start for creating an empty Intermarché item
    this.service = null;
  }

  static _connect(){
    this.service = new WebSocket("ws://localhost:1963/alasvaladas/WebSockets");
    this.service.onmessage = (event) => {
      Swal.close();
      event.data !== undefined ? Leclerc_items._update(JSON.parse(event.data)) : Leclerc_items._display_error();
    };
    this.service.onopen = () => {
      console.log("service.onopen...");
      let response = window.confirm(this.service.url + " just opened... Say 'Hi!'?");
      if (response) this.service.send(JSON.stringify({Response: "Hi!"}));
    };
    this.service.onclose = (event/*:CloseEvent*/) => {
      console.log("service.onclose... " + event.code);
      window.alert("Bye! See you later...");
        // '1011': the server is terminating the connection because it encountered an unexpected condition that prevented it from fulfilling the request.
    };
    this.service.onerror = () => {
      window.alert("service.onerror...");
    };
  }

  static _send_data(data){
      console.log(JSON.stringify(data));
    if(data.value.length !== 0) {
      this.service.send(JSON.stringify(data));
      Swal.fire({
        title: 'Recherche en cours'
      });
      Swal.showLoading();
    }
  }

  static _update(items, index = 0){
    console.log(items[0]);
    let display = window.document.getElementById("container");
    switch (index) {
      case items.length:
        index = 0;
        display.innerHTML = Leclerc_items._representation(items, index);
        break;
      case -1:
        index = items.length-1;
        display.innerHTML = Leclerc_items._representation(items, index);
        break;
      default: display.innerHTML = Leclerc_items._representation(items, index);
    }
    window.document.getElementById('previous').addEventListener('click', (event) => {
      event.stopImmediatePropagation();
      Leclerc_items._update(items, index-1);
    });
    window.document.getElementById('next').addEventListener('click', (event) => {
      event.stopImmediatePropagation();
      Leclerc_items._update(items, index+1);
    });
  }

  static _representation(items, index){
    let control_panel = items.length !== 1 && items.length !== 0 ? `
    <div class="card-footer bg-dark text-white">
      <div class="text-center bg-dark text-white">
        <button href="#" id="previous" class="btn btn-dark">&#10094;</button>
        <button href="#" id="next" class="btn btn-dark">&#10095;</button>
      </div>
    </div>` : '';
    let description = Leclerc_items._showDetails(items[index]._desc);
    let ingrediants = Leclerc_items._showIngrediants(items[index]._compo);

    return `
    <div id="items_display" class="card bg-secondary border border-white" style="width: 18rem;">
      <img src="${items[index]._img_url}" class="card-img-top bg-dark text-white" alt="..." height=350 width=350>
      <div class="card-body bg-dark text-white">
        <h5 class="card-title text-center">${items[index]._nom}</h5>
        ${description}
      </div>
      <ul class="list-group list-group-flush">
        <li class="list-group-item bg-dark text-white text-center">${items[index]._prix_unit}</li>
        <li class="list-group-item bg-dark text-white">
          ${ingrediants}
        </li>
      </ul>
      ${control_panel}
    </div>
    `;
  }

  static _showDetails(text){
    return text === undefined ? `<p class="card-text">Aucune description n'est disponible</p>` : `
    <li class="list-group-item bg-dark text-white">
      <button class="btn btn-dark" aria-expanded="false" aria-controls="collapseExample" data-toggle="collapse" data-target="#desc">Description du produit &#9776</button>
      <div id="desc" class="collapse bg-dark text-white text">
        ${text}
      </div>
    </li>`;
  }

  static _showIngrediants(ingrediants){
    return ingrediants !== undefined ? `<button class="btn btn-dark" aria-expanded="false" aria-controls="collapseExample" data-toggle="collapse" data-target="#composition">Voir la composition du produit</button>
    <div id="composition" class="collapse bg-dark text-white">
      ${ingrediants}
    </div>` : `<p class="card-text">La composition pour ce produit n'est pas fournie...</p>`
  }

  static _display_error(){
    Swal.fire({
      title: 'Erreur lors de la récupération des données...',
      icon: 'error'
    });
  }
}

Leclerc_items._connect();
