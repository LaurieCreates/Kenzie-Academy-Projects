import BaseClass from "../util/baseClass";
import DataStore from "../util/DataStore";
import CreateArtworkClient from "../api/createArtworkClient1";

/**
 * Logic needed for the view playlist page of the website.
 */
class CreatePage extends BaseClass {

    constructor() {
        super();
        this.bindClassMethods(['onCreate', 'renderExample'], this);
        this.dataStore = new DataStore();
    }

    /**
     * Once the page has loaded, set up the event handlers and fetch the concert list.
     */
    async mount() {
        document.getElementById('create-artwork-form').addEventListener('submit', this.onCreate);
        this.client = new CreateArtworkClient();
        this.dataStore.addChangeListener(this.renderExample);
    }

    // Render Methods --------------------------------------------------------------------------------------------------

    async renderExample() {
        let resultArea = document.getElementById("create-artwork-form");

        const artwork = this.dataStore.get("createdArtwork");

        if (artwork) {
            resultArea.innerHTML = `
            <body>
                <h3 style = "background: -webkit-linear-gradient(#000000, #008080,#0000FF)";
                                     -webkit-background-clip: text;
                                     -webkit-text-fill-color: transparent;>
             <label style="text-align: center">
                Name: ${artwork.artistName}<br>
                Title: ${artwork.title}<br>
                DateCreated: ${artwork.dateCreated}<br>
                Height: ${artwork.height}<br>
                Width: ${artwork.width}<br>
                ForSale: ${artwork.forSale}<br>
                Price: ${artwork.price}<br>
                ID: ${artwork.id}<br>
                Date Posted: ${artwork.datePosted}<br>
                Is Sold: ${artwork.sold}<br>
                Your upload was successful!
                </label>
                </h3>
                <br>
                <br>
                <br>
                <br>
                <br>
                <br>
                <br>
                <br>
                <br>
                <br><br>
                <br>
                <br>
                <br>
                <br>
                <br>
                <br>
                <br>
                <br>
                <br>
                <br>
                                <br>
                        <img src = http://3.bp.blogspot.com/-RhNvU7h2FL4/TxjruKgsv3I/AAAAAAAACps/jzIZuIOxoRs/s1600/AndyWarhol-Marilyn-Monroe-1962.jpg:>
                <label style="text-align: center">
                <a href="mailto:email@sample.com">CONTACT US</a>
                </label>
                </body>
            `
        } else {
            resultArea.innerHTML = "No Item";
        }
    }

    // Event Handlers --------------------------------------------------------------------------------------------------

//    async onGet(event) {
//        // Prevent the page from refreshing on form submit
//        event.preventDefault();
//
////        let id = document.getElementById('create-artwork-form').value;
////        this.dataStore.set("id", null);
//
//        let result = await this.client.getArtwork(id, this.errorHandler);
//        this.dataStore.set("id", result);
//        if (result) {
//            this.showMessage(`Got ${result.id}!`)
//        } else {
//            this.errorHandler("Error doing GET!  Try again...");
//        }
//    }

    async onCreate(event) {
        // Prevent the page from refreshing on form submit
        event.preventDefault();

        let artistName = document.getElementById("create-artwork-artist").value;
        let title = document.getElementById("create-artwork-title").value;
        let dateCreated = document.getElementById("create-artwork-dateCreated").value;
        let height = document.getElementById("create-artwork-height").value;
        let width = document.getElementById("create-artwork-width").value;
        let forSale = document.getElementById("create-artwork-isForSale").value;
        let price = document.getElementById("create-artwork-price").value;

        const createdArtwork = await this.client.addNewArtwork(artistName, title, dateCreated,
            height, width, forSale, price);
        this.dataStore.set("createdArtwork", createdArtwork);

        if (createdArtwork) {
            this.showMessage(`Created ${createdArtwork.artistName}!`)
        } else {
            this.errorHandler("Error creating!  Try again...");
        }
    }
}

/**
 * Main method to run when the page contents have loaded.
 */
const main = async () => {
    const createPage = new CreatePage();
    createPage.mount();
};

window.addEventListener('DOMContentLoaded', main);
