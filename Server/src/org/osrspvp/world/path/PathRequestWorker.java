package org.osrspvp.world.path;

import org.osrspvp.model.Client;
import org.osrspvp.model.npc.NPC;
import org.osrspvp.util.cache.region.Region;

public final class PathRequestWorker implements Runnable {

    protected int finalX;
    protected int finalY;
    protected Client c;

    public PathRequestWorker(Client c, Client a) {
        this.c = c;
        this.finalX = a.getX();
        this.finalY = a.getY();
        int dX = a.getX() - c.absX;
        int dY = a.getY() - c.absY;
        dX = dX > 1 ? 1 : (dX < -1 ? -1 : dX);
        dY = dY > 1 ? 1 : (dY < -1 ? -1 : dY);
        if (dX != 0 && dY != 0) {
            if (Region.canMove(a.getX(), a.getY(), this.finalX - dX, this.finalY, c.heightLevel % 4, 1, 1)) {
                this.finalX -= dX;
            } else if (Region.canMove(a.getX(), a.getY(), this.finalX, this.finalY - dY, c.heightLevel % 4, 1, 1)) {
                this.finalY -= dY;
            }
        } else if (dX != 0 && Region.canMove(a.getX(), a.getY(), this.finalX - dX, this.finalY, c.heightLevel % 4, 1, 1)) {
            this.finalX -= dX;
        } else if (dY != 0 && Region.canMove(a.getX(), a.getY(), this.finalX, this.finalY - dY, c.heightLevel % 4, 1, 1)) {
            this.finalY -= dY;
        }

    }

    public PathRequestWorker(Client c, NPC a) {
        this.c = c;
        this.finalX = a.getX();
        this.finalY = a.getY();
        int dX = a.getX() - c.absX;
        int dY = a.getY() - c.absY;
        dX = dX > 1 ? 1 : (dX < -1 ? -1 : dX);
        dY = dY > 1 ? 1 : (dY < -1 ? -1 : dY);
        if (dX != 0 && dY != 0) {
            if (Region.canMove(a.getX(), a.getY(), this.finalX - dX, this.finalY, c.heightLevel % 4, 1, 1)) {
                this.finalX -= dX;
            } else if (Region.canMove(a.getX(), a.getY(), this.finalX, this.finalY - dY, c.heightLevel % 4, 1, 1)) {
                this.finalY -= dY;
            }
        } else if (dX != 0 && Region.canMove(a.getX(), a.getY(), this.finalX - dX, this.finalY, c.heightLevel % 4, 1, 1)) {
            this.finalX -= dX;
        } else if (dY != 0 && Region.canMove(a.getX(), a.getY(), this.finalX, this.finalY - dY, c.heightLevel % 4, 1, 1)) {
            this.finalY -= dY;
        }

    }

    @Override
    public void run() {
        Region.findRoute(this.c, this.finalX, this.finalY, false, 1, 1);
    }
}
