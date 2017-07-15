/**
 * @author Suraj Rawat <suraj.raw120@gmail.com>
 *
 * @license AGPL-3.0
 *
 * This code is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License, version 3,
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */

package com.procleus.brime.models;

import java.sql.Timestamp;

public class TextNote {
    public int id;
    public String note;
    public String title;
    public Timestamp created;
    public Timestamp edited;
    public int owner;

    public TextNote(int i, String n, String t, Timestamp c, Timestamp e, int o) {
        this.id = i;
        this.note = n;
        this.title = t;
        this.created = c;
        this.edited = e;
        this.owner = o;
    }
}
