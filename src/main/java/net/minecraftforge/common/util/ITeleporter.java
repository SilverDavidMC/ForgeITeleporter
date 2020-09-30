/*
 * Minecraft Forge
 * Copyright (c) 2016-2020.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.common.util;

import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.TeleportationRepositioner;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;

import java.util.Optional;
import java.util.function.Function;

/**
 * Interface for handling the placement of entities during dimension change.
 * <p>
 * An implementation of this interface can be used to place the entity
 * in a safe location, or generate a return portal, for instance.
 * <p>
 * See the {@link net.minecraft.world.Teleporter} class, which has
 * been patched to implement this interface, for a vanilla example.
 */
public interface ITeleporter
{
    default Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw,
            Function<Boolean, Entity> repositionEntity)
    {
        return repositionEntity.apply(true);
    }
    
    /**
     * Is this teleporter the vanilla instance.
     */
    default boolean isVanilla()
    {
        return false;
    }
    
    /**
     * Finds a portal to teleport to and creates a {@link TeleportationRepositioner.Result} for it.
     * Return an empty {@link Optional} if no portal was found.
     */
    Optional<TeleportationRepositioner.Result> findPortal(ServerWorld fromWorld, ServerWorld toWorld, Entity entity);
    
    /**
     * Creates a portal if one doesn't exist and returns the {@link TeleportationRepositioner.Result Result}.
     */
    Optional<TeleportationRepositioner.Result> createAndGetPortal(ServerWorld fromWorld, ServerWorld toWorld,
            Entity entity);
    
    /**
     * A default implementation of {@link ITeleporter#getPortalInfo(TeleportationRepositioner.Result)} that sets the yaw and pitch to 0.
     * <p>
     * To maintain the yaw and pitch of the original entity, see {@link TeleporterHelper#getPortalInfo(TeleportationRepositioner.Result, Entity, ServerWorld)}.
     */
    default PortalInfo getPortalInfo(TeleportationRepositioner.Result tpResult)
    {
        return new PortalInfo(new Vector3d(tpResult.field_243679_a.getX(), tpResult.field_243679_a.getY(),
                tpResult.field_243679_a.getZ()), Vector3d.ZERO, 0, 0);
    }
}
