package com.redis.api

import com.redis.GeoRadiusMember
import com.redis.serialization.{Format, Parse}

trait GeoApi {

  /**
   * Add the given <code>members</code> in the <code>key</code> geo sorted set
   *
   * @param key     The geo sorted set
   * @param members The members to be added. Format is (longitude, latitude, member)
   * @return The number of elements added to the index. Repeated elements are not added.
   */
  def geoadd(key: Any, members: Iterable[Product3[Any, Any, Any]]): Option[Int]

  /**
   * Retrieve the position of the members in the key geo sorted set. Note that if a member is not part of the set, None
   * will be returned for this element.
   *
   * @param key
   * @param members
   * @param format
   * @param parse
   * @tparam A
   * @return the coordinates of the input members in the same order.
   */
  def geopos[A](key: Any, members: Iterable[Any])(implicit format: Format, parse: Parse[A]): Option[List[Option[List[Option[A]]]]]

  /**
   * Get the geohash for each member in the key geo index.
   *
   * @param key
   * @param members
   * @param format
   * @param parse
   * @tparam A
   * @return The geohash of each queried member.
   */
  def geohash[A](key: Any, members: Iterable[Any])(implicit format: Format, parse: Parse[A]): Option[List[Option[A]]]

  def geodist(key: Any, m1: Any, m2: Any, unit: Option[Any]): Option[String]

  /**
   * Search for members around an origin point in the key geo sorted set
   *
   * @param key       The geo index we are searching in
   * @param longitude The base longitude for distance computation
   * @param latitude  The base latitude for distance computation
   * @param radius    The radius of the circle we want to search in
   * @param unit      The unit of the radius. Can be m (meters), km (kilometers), mi (miles), ft (feet)
   * @param withCoord If true, the coordinate of the found members will be returned in the result
   * @param withDist  If true, the distance between the origin and the found members will be returned in the result
   * @param withHash  If true, the hash of the found members will be returned in the result
   * @param count     Max number of expected results
   * @param sort      The sorting strategy. If empty, order is not guaranteed. Can be ASC (ascending) or DESC (descending)
   * @param store     The Redis store we want to write the result in
   * @param storeDist The redis storedist we want to write the result in
   * @return The found members as GeoRadiusMember instances
   */
  def georadius(key: Any,
                longitude: Any,
                latitude: Any,
                radius: Any,
                unit: Any,
                withCoord: Boolean,
                withDist: Boolean,
                withHash: Boolean,
                count: Option[Int],
                sort: Option[Any],
                store: Option[Any],
                storeDist: Option[Any]): Option[List[Option[GeoRadiusMember]]]

  /**
   * Search for members around a specific memberin the key geo sorted set
   *
   * @param key       The geo index we are searching in
   * @param member    The member we are searching around
   * @param radius    The radius of the circle we want to search in
   * @param unit      The unit of the radius. Can be m (meters), km (kilometers), mi (miles), ft (feet)
   * @param withCoord If true, the coordinate of the found members will be returned in the result
   * @param withDist  If true, the distance between the origin and the found members will be returned in the result
   * @param withHash  If true, the hash of the found members will be returned in the result
   * @param count     Max number of expected results
   * @param sort      The sorting strategy. If empty, order is not guaranteed. Can be ASC (ascending) or DESC (descending)
   * @param store     The Redis store we want to write the result in
   * @param storeDist The redis storedist we want to write the result in
   * @return The found members as GeoRadiusMember instances
   */
  def georadiusbymember[A](key: Any,
                           member: Any,
                           radius: Any,
                           unit: Any,
                           withCoord: Boolean,
                           withDist: Boolean,
                           withHash: Boolean,
                           count: Option[Int],
                           sort: Option[Any],
                           store: Option[Any],
                           storeDist: Option[Any])(implicit format: Format, parse: Parse[A]): Option[List[Option[GeoRadiusMember]]]

}
