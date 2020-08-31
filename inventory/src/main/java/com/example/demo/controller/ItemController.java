package com.example.demo.controller;

import com.example.demo.exception.InsufficientQuantityException;
import com.example.demo.exception.ItemNotExistException;
import com.example.demo.model.entity.Item;
import com.example.demo.model.entity.Response;
import com.example.demo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemService itemService;

    //save new item
    //url : /item
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public ResponseEntity<Response<Item>> saveItem(@RequestBody Item item){

        Item it = itemService.saveItem(item);

        Response<Item> response = new Response<>();

        response.setSuccess(true);
        response.setData(it);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //update item
    //url : /item
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PUT)
    public ResponseEntity<Response<Item>> updateItemDetail(@RequestBody Item item){

        Response<Item> response = new Response<>();
        try {
            Item updatedItem = itemService.updateItemInfo(item);

            response.setSuccess(true);
            response.setData(updatedItem);

        }catch (ItemNotExistException e) {
            response.setSuccess(false);
            response.setMessage(e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //getItemDetail(id)
    //url : /item/12345
    @RequestMapping(value = "/{itemId}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public ResponseEntity<Response<Item>> getItemDetail(@PathVariable long itemId){

        Response<Item> response = new Response<>();
        try {
            Item item = itemService.getItemDetail(itemId);

            response.setSuccess(true);
            response.setData(item);

        }catch (ItemNotExistException e) {
            response.setSuccess(false);
            response.setMessage(e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //receiveItem(id, quantity)
    //url : /item/receive/12345/quantity?q=1
    @RequestMapping(value = "/receive/{itemId}/quantity", produces = MediaType.APPLICATION_JSON_VALUE, method =
            RequestMethod.PUT)
    public ResponseEntity<Response<Item>> receiveItem(@PathVariable("itemId") int itemId, @RequestParam("q")int quantity){

        Response<Item> response = new Response<>();
        try {
            Item item = itemService.receiveItem(itemId, quantity);

            response.setSuccess(true);
            response.setData(item);

        } catch (ItemNotExistException e) {
            response.setSuccess(false);
            response.setMessage(e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //issueItem(id, quantity)
    //url : /item/issue/12345/quantity?q=1
    @RequestMapping(value = "/issue/{itemId}/quantity", produces = MediaType.APPLICATION_JSON_VALUE, method =
            RequestMethod.PUT)
    public ResponseEntity<Response<Item>> issueItem(@PathVariable("itemId") int itemId, @RequestParam("q")int quantity){

        Response<Item> response = new Response<>();
        try {
            Item item = itemService.issueItem(itemId, quantity);

            response.setSuccess(true);
            response.setData(item);

        } catch (ItemNotExistException e) {
            response.setSuccess(false);
            response.setMessage(e.toString());
        } catch (InsufficientQuantityException e) {
            response.setSuccess(false);
            response.setMessage(e.toString());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
