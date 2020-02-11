/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package info.revenberg.song.business.entities;

import java.util.ArrayList;
import java.util.List;

public class SeedStarter {

    private Integer id = null;
    private Long bundleid = null;
    private Long songid;

    private String datePlanted1 = null;
    private Boolean covered = null;
//    private MyType type = MyType.PLASTIC;
    private String bundle = null;
    private String song = null;

    private String versesvalue;
    private String versesid;

    private List<Row> rows = new ArrayList<Row>();

//    String[] multiCheckboxSelectedValues;

    public SeedStarter() {
        super();
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Long getBundleid() {
        return this.bundleid;
    }

    public void setBundleid(final Long bundleid) {
        this.bundleid = bundleid;
    }
    
    public Long getSongid() {
        return this.songid;
    }

    public void setSongid(final Long songid) {
        this.songid = songid;
    }

    
    public String getVersesid() {
        return this.versesid;
    }

    public void setVersesid(final String versesid) {
        this.versesid = versesid;
    }

    public String getDatePlanted1() {
        return this.datePlanted1;
    }

    public void setDatePlanted1(final String datePlanted1) {
        this.datePlanted1 = datePlanted1;
    }

    public String getBundle() {
        return this.bundle;
    }

    public void setBundle(final String bundle) {
        this.bundle = bundle;
    }

    public void setSong(final String song) {
        this.song = song;
    }

    public String getSong() {
        return this.song;
    }

    public Boolean getCovered() {
        return this.covered;
    }

    public void setCovered(final Boolean covered) {
        this.covered = covered;
    }

//    public MyType getType() {
//        return this.type;
//    }

//    public void setType(final MyType type) {
//        this.type = type;
//    }

    public String getVersesvalue() {
        System.out.println("???????????????????? getVersesvalue ????????????????????????????????????????????");
        return this.versesvalue;
    }

    public void setVersesvalue(String versesvalue) {
        System.out.println("???????????????????? setVersesvalue ????????????????????????????????????????????");
        this.versesvalue = versesvalue;
    }

    public List<Row> getRows() {
        return this.rows;
    }

    @Override
    public String toString() {

        String rc;
        if (versesvalue == null) {
            rc = "SeedStarter [id=" + this.id 
                + ", bundleid=" + this.bundleid 
                + ", songid=" + this.songid 
                + ", rows=" + this.rows + "]";
        } else {
            rc = "SeedStarter [id=" + this.id 
                + ", bundleid=" + this.bundleid 
                + ", songid=" + this.songid 
                + ", versesvalue=(" + this.versesvalue + ")" 
                + ", rows=" + this.rows + "]";
        }
        return rc;
    }

}
