package app.controller;

import app.model.Localitate;
import app.model.Regiune;
import app.model.SportExtrem;
import app.model.Tara;
import app.service.Service;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class Controller {
    private final Service<Localitate> localitateService;
    private final Service<Regiune> regiuneService;
    private final Service<Tara> taraService;
    private final Service<SportExtrem> sportExtremService;

    public Controller(Service<Localitate> campaignService, Service<Regiune> advertiserService, Service<Tara> publisherService, Service<SportExtrem> userService) {
        this.localitateService = campaignService;
        this.regiuneService = advertiserService;
        this.taraService = publisherService;
        this.sportExtremService = userService;
    }

    /**
     *
     * @param localitate locality name
     * @param regiune region name
     * @param tara country name
     * @return information about a location
     *
     * Return information about a specified location
     *
     * @throws IOException
     */
    @GetMapping(path="/location", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Localitate>  getAllInformation(@RequestParam(name = "loc", required = false) String localitate,
                                               @RequestParam(name = "reg", required = false) String regiune,
                                               @RequestParam(name = "ctr", required = false) String tara) throws IOException {
        List<Localitate> locs = new ArrayList<Localitate>(localitateService.getAllInfo().values());
        if(tara != null) {
            locs = locs.stream()
                    .filter(loc -> loc.getNumeTara().equals(tara))
                    .collect(Collectors.toList());
        }
        if(regiune != null){
            locs = locs.stream()
                    .filter(loc -> loc.getNumeRegiune().equals(regiune))
                    .collect(Collectors.toList());
        }
        if(localitate != null) {
            locs = locs.stream()
                    .filter(loc -> loc.getNumeLocalitate().equals(localitate))
                    .collect(Collectors.toList());
        }
        return locs;
    }

    /**
     *
     * @param localitate locality name
     * @param regiune region name
     * @param tara country name
     *
     * Delete the specified location
     *
     * @throws IOException
     */
    @DeleteMapping(path = "/delloc", params = {"loc","reg", "ctr"})
    public void  deleteLocation(@RequestParam(name = "loc") String localitate,
                                @RequestParam(name = "reg") String regiune,
                                @RequestParam(name = "ctr") String tara) throws IOException {
        localitateService.del(localitate, regiune, tara);

    }

    /**
     *
     * @param local object with new data
     * @param localitate locality name
     * @param regiune region name
     * @param tara country name
     *
     * Modify location in database
     *
     * @throws IOException
     */
    @PatchMapping(path = "/modifyloc", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void modifyLocation(@RequestBody Localitate local,
                               @RequestParam(name = "loc") String localitate,
                               @RequestParam(name = "reg") String regiune,
                               @RequestParam(name = "ctr") String tara) throws IOException {
        List<Localitate> locs = new ArrayList<Localitate>(localitateService.getAllInfo().values());
        List<Localitate> l = locs.stream()
                .filter(loc -> loc.getNumeLocalitate().equals(localitate))
                .filter(loc -> loc.getNumeRegiune().equals(regiune))
                .filter(loc -> loc.getNumeTara().equals(tara))
                .collect(Collectors.toList());
        Localitate location = l.get(0);

        Map mapTara = taraService.getAll(Tara.class);
        Map mapRegiune = regiuneService.getAll(Regiune.class);
        Map mapLocalitate = localitateService.getAll(Localitate.class);

        int indexCountry = taraService.getKey(mapTara, location.getNumeTara());
        int indexRegion = regiuneService.getKey(mapRegiune, location.getNumeRegiune());
        int indexLocality = localitateService.getKey(mapLocalitate, location.getNumeLocalitate());
        if (local.getNumeTara() != null) {
            taraService.modify(Tara.class, indexCountry, local.getNumeTara(), "nume");
        }
        if(local.getNumeRegiune() != null) {
            regiuneService.modify(Regiune.class, indexRegion, local.getNumeRegiune(), "nume");
        }
        if(local.getNumeLocalitate() != null) {
            localitateService.modify(Localitate.class, indexLocality, location.getNumeLocalitate(), "nume");
        }
        if(local.getSport() != null) {
            sportExtremService.modifyList(local.getSport(), location.getSport(), indexLocality);
        }

    }

    /**
     *
     * @param loc location object
     *
     * Add a new location to database
     *
     * @throws IOException
     */
    @PostMapping(path="/addloc", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void  addLocation(@RequestBody Localitate loc) throws IOException {
        int indexCountry;
        int indexRegion;
        int indexLocality;

        Map mapTara = taraService.getAll(Tara.class);
        Map mapRegiune = regiuneService.getAll(Regiune.class);
        Map mapLocalitate = localitateService.getAll(Localitate.class);

        if(!mapTara.containsValue(loc.getNumeTara())) {
            indexCountry = taraService.add(Tara.class, loc.getNumeTara(), -1);
        }
        else {
            indexCountry = taraService.getKey(mapTara, loc.getNumeTara());
        }
        if(!mapRegiune.containsValue(loc.getNumeRegiune())) {
            indexRegion = regiuneService.add(Regiune.class, loc.getNumeRegiune(), indexCountry);
        }
        else {
            indexRegion = regiuneService.getKey(mapRegiune, loc.getNumeRegiune());
        }
        if(!mapLocalitate.containsValue(loc.getNumeLocalitate())) {
            indexLocality = localitateService.add(Localitate.class, loc.getNumeLocalitate(), indexRegion);
        }
        else {
            indexLocality = localitateService.getKey(mapLocalitate, loc.getNumeLocalitate());
        }
        localitateService.addList(loc.getSport(), indexLocality);

    }

    /**
     *
     * @param sports list of sport to look for
     * @param ziStart day start month
     * @param lunaStart start month
     * @param ziEnd day end month
     * @param lunaEnd end month
     * @return locations with that list of sports available
     *
     * @throws IOException
     */
    @GetMapping(path="/findLoc", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Localitate>  findPlaces(@RequestParam(name = "sport", required = false) ArrayList<String>  sports,
                                        @RequestParam(name = "ziStart", required = false) Integer ziStart,
                                        @RequestParam(name = "lunaStart", required = false) String lunaStart,
                                        @RequestParam(name = "ziEnd", required = false) Integer ziEnd,
                                        @RequestParam(name = "lunaEnd", required = false) String lunaEnd) throws IOException {
        if((ziStart != null && ziStart < 0) || (ziEnd != null && ziEnd > sportExtremService.days.get(lunaStart))) {
            return null;
        }
        List<Localitate> locs = new ArrayList<Localitate>(localitateService.getAllInfo().values());
        if (sports != null) {
            locs = locs.stream()
                    .filter(loc -> (loc.getSport().stream()
                            .filter(sp -> sports.contains(sp.getNumeSport()))
                            .collect(Collectors.toList())).size() > 0)
                    .collect(Collectors.toList());
        }
        if (lunaStart != null && lunaEnd != null) {
            if (sports != null) {
                locs = locs.stream()
                        .filter(loc -> (loc.getSport().stream()
                                .filter(sp -> sports.contains(sp.getNumeSport()) &&
                                        Arrays.asList(sportExtremService.getMonths(sp.getStartDate(), sp.getEndDate())).containsAll(Arrays.asList(sportExtremService.getMonths(lunaStart, lunaEnd))))
                                .collect(Collectors.toList())).size() > 0)
                        .collect(Collectors.toList());
            }
            else {
                locs = locs.stream()
                        .filter(loc -> (loc.getSport().stream()
                                .filter(sp -> Arrays.asList(sportExtremService.getMonths(sp.getStartDate(), sp.getEndDate())).containsAll(Arrays.asList(sportExtremService.getMonths(lunaStart, sp.getEndDate()))))
                                .collect(Collectors.toList())).size() > 0)
                        .collect(Collectors.toList());
            }
        }
        Collections.sort(locs);
        return locs;
    }

}
